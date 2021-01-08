package com.github.jimmmisss.rango.cadastro;

import com.github.jimmmisss.rango.cadastro.dto.input.RestauranteInput;
import com.github.jimmmisss.rango.cadastro.dto.RestauranteMapper;
import com.github.jimmmisss.rango.cadastro.dto.output.RestauranteOutput;
import com.github.jimmmisss.rango.cadastro.dto.input.RestauranteUpdateInput;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Restaurante")
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @GET
    public List<RestauranteOutput> listaRestaurantes() {
        List<Restaurante> restaurantes = Restaurante.listAll();
        return restaurantes.stream().map(restauranteMapper::toRestauranteOutput)
            .collect(Collectors.toList());
    }

    @POST
    @Transactional
    public Response salvar(RestauranteInput restauranteInput) {
        Restaurante restaurante = restauranteMapper.toRestaurante(restauranteInput);
        restaurante.persist();
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, RestauranteUpdateInput restauranteUpdateInput) {
        Optional<Restaurante> restauranteResult = Restaurante.findByIdOptional(id);
        if (restauranteResult.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteMapper.toRestaurante(restauranteUpdateInput, restauranteResult.get());
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
