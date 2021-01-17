package com.github.jimmmisss.rango.cadastro.resource;

import com.github.jimmmisss.rango.cadastro.dto.input.RestauranteInput;
import com.github.jimmmisss.rango.cadastro.dto.mapper.RestauranteMapper;
import com.github.jimmmisss.rango.cadastro.dto.output.RestauranteOutput;
import com.github.jimmmisss.rango.cadastro.entity.Restaurante;
import com.github.jimmmisss.rango.cadastro.infra.ConstraintViolationResponse;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "rango-auth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/rango/protocol/openid-connect/token")))
@SecurityRequirement(name = "rango-auth", scopes = {})
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
    @APIResponse(responseCode = "201", description = "Caso restaurante seja cadastrado com sucesso")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    public Response salvar(@Valid RestauranteInput restauranteInput) {
        Restaurante restaurante = restauranteMapper.toRestaurante(restauranteInput);
        restaurante.persist();
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, RestauranteInput restauranteInput) {
        Optional<Restaurante> restauranteResult = Restaurante.findByIdOptional(id);
        if (restauranteResult.isEmpty()) {
            throw new NotFoundException("Restaurante não existe.");
        }
        Restaurante restaurante = restauranteMapper.toRestaurante(restauranteInput, restauranteResult.get());
        restaurante.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteResult = Restaurante.findByIdOptional(id);
        restauranteResult.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException("Restaurante não existe.");
        });
    }
}
