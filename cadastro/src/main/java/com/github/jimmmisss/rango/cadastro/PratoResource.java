package com.github.jimmmisss.rango.cadastro;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Prato")
public class PratoResource {

    @GET
    @Path("{idRestaurante}/pratos")
    public List<Prato> buscaPratos(@PathParam("idRestaurante") Long idRestaurante) {
        return Prato.list("restaurante", getRestaurante(idRestaurante));
    }

    @POST
    @Path("idRestaurante/pratos")
    @Transactional
    public Response salva(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
        Prato prato = new Prato();
        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.preco = dto.preco;
        prato.restaurante = getRestaurante(idRestaurante);
        dto.persist();
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("{idRestaurante}/pratos/{idPrato}")
    @Transactional
    public void atualiza(@PathParam("idRestaurante") Long idRestaurante,
                         @PathParam("idPrato") Long idPrato, Prato dto) {
        getRestaurante(idRestaurante);
        Prato prato = getPrato(idPrato);
        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.preco = dto.preco;
        prato.persist();
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
