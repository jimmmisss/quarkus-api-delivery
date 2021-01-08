package com.github.jimmmisss.rango.cadastro.resource;

import com.github.jimmmisss.rango.cadastro.dto.PratoMapper;
import com.github.jimmmisss.rango.cadastro.dto.input.PratoInput;
import com.github.jimmmisss.rango.cadastro.dto.input.PratoUpdateInput;
import com.github.jimmmisss.rango.cadastro.dto.output.PratoOutput;
import com.github.jimmmisss.rango.cadastro.entity.Prato;
import com.github.jimmmisss.rango.cadastro.entity.Restaurante;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Prato")
public class PratoResource {

    @Inject
    PratoMapper pratoMapper;

    @GET
    @Path("{idRestaurante}/pratos")
    public List<PratoOutput> buscaPratos(@PathParam("idRestaurante") Long idRestaurante) {
        Stream<Prato> restaurante = Prato.stream("restaurante", getRestaurante(idRestaurante));
        return restaurante.map(pratoMapper::toPratoOutput).collect(Collectors.toList());
    }

    @POST
    @Path("idRestaurante/pratos")
    @Transactional
    public Response salva(@PathParam("idRestaurante") Long idRestaurante, PratoInput pratoInput) {
        getRestaurante(idRestaurante);
        Prato prato = pratoMapper.toPrato(pratoInput);
        prato.persist();
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("{idRestaurante}/pratos/{idPrato}")
    @Transactional
    public void atualiza(@PathParam("idRestaurante") Long idRestaurante, @PathParam("idPrato") Long idPrato, PratoUpdateInput pratoUpdateInput) {
        getRestaurante(idRestaurante);
        pratoMapper.toPrato(pratoUpdateInput, getPrato(idPrato)).persist();
    }

    @DELETE
    @Path("{idRestaurante}/pratos/{idPrato}")
    @Transactional
    public void delete(@PathParam("idRestaurante") Long idRestaurante,
                       @PathParam("idPrato") Long idPrato) {
        getRestaurante(idRestaurante);
        Optional<Prato> pratoResult = Prato.findByIdOptional(idPrato);
        pratoResult.ifPresentOrElse(Prato::delete, () -> {
            throw new NotFoundException();
        });
    }

    private Restaurante getRestaurante(@PathParam("idRestaurante") Long idRestaurante) {
        Optional<Restaurante> restauranteResult = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteResult.isEmpty()) {
            throw new NotFoundException("Restaurante não existe.");
        }
        return restauranteResult.get();
    }

    private Prato getPrato(@PathParam("idPrato") Long idPrato) {
        Optional<Prato> pratoResult = Prato.findByIdOptional(idPrato);
        if (pratoResult.isEmpty()) {
            throw new NotFoundException("Prato não existe.");
        }
        return pratoResult.get();
    }

}
