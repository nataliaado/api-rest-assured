package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String readJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void createPet() throws IOException {
        String jsonBody = readJson("C:/PetStore/src/test/resources/data/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Atenas"))
                .body("status", is("avaliable"));
    }
}
