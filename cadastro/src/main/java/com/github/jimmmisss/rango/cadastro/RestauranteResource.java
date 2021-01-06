package com.github.jimmmisss.rango.cadastro;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource {

    @GET
    public List<Restaurante> listaRestaurantes() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    public Response salvar(Restaurante dto) {
        dto.persist();
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, Restaurante dto) {
        Optional<Restaurante> restauranteResult = Restaurante.findByIdOptional(id);
        if (restauranteResult.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteResult.get();
        restaurante.nome = dto.nome;
        restaurante.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteResult = Restaurante.findByIdOptional(id);
        restauranteResult.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });
    }
}
