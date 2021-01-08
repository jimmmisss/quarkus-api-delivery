package com.github.jimmmisss.rango.cadastro;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteResourceTest {

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testRetornaTodosRestaurantes() {
        String resultado = given()
            .when().get("/restaurantes")
            .then()
            .statusCode(200)
            .extract().asString();
        Approvals.verifyJson(resultado);
    }

    private RequestSpecification given() {
        return RestAssured.given().contentType(ContentType.JSON);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testAlterarRestaurante() {
        Restaurante restaurante = new Restaurante();
        restaurante.nome = "novoNome";
        Long parameterValue = 123L;
        given()
            .with().pathParam("id", parameterValue)
            .body(restaurante)
            .when().put("/restaurante/{id}")
            .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode())
            .extract().asString();

        Restaurante restauranteResult = Restaurante.findById(parameterValue);
        Assertions.assertEquals(restaurante.nome, restauranteResult.nome);

    }

}










