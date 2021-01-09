package com.github.jimmmisss.rango.cadastro.resource;

import com.github.jimmmisss.rango.cadastro.dto.input.LocalizacaoInput;
import com.github.jimmmisss.rango.cadastro.dto.mapper.LocalizacaoMapper;
import com.github.jimmmisss.rango.cadastro.dto.output.LocalizacaoOutput;
import com.github.jimmmisss.rango.cadastro.entity.Localizacao;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@Path("/localizacaoes")
@Tag(name = "Localização")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocalizacaoResource {

    @Inject
    LocalizacaoMapper localizacaoMapper;

    @GET
    public List<LocalizacaoOutput> listaTodos() {
        List<Localizacao> localizacoes = Localizacao.listAll();
        return localizacoes.stream().map(localizacaoMapper::toLocalizacaoOutput)
            .collect(Collectors.toList());
    }

    @POST
    @Transactional
    public Response salvar(LocalizacaoInput localizacaoInput) {
        Localizacao localizacao = localizacaoMapper.toLocalizacao(localizacaoInput);
        localizacao.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, LocalizacaoInput localizacaoInput) {
        Optional<Localizacao> localizacaoRersult = getLocalizacao(id);
        Localizacao localizacao = localizacaoMapper.toLocalizacao(localizacaoInput, localizacaoRersult.get());
        localizacao.persist();
        return Response.status(NO_CONTENT).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        Optional<Localizacao> localizacao = getLocalizacao(id);
        localizacao.ifPresent(PanacheEntityBase::delete);
        return Response.status(NO_CONTENT).build();
    }

    private Optional<Localizacao> getLocalizacao(Long id) {
        Optional<Localizacao> localizacaoRersult = Localizacao.findByIdOptional(id);
        if (localizacaoRersult.isEmpty()) {
            throw new NotFoundException("Localização não existe");
        }
        return localizacaoRersult;
    }



}
