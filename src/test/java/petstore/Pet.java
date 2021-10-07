package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String readJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test(priority = 1)
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
                .body("status", is("available"))
                .body("category.name", is("AT49FJE0L"))
                .body("tags.name", contains("data"));
    }

    @Test(priority = 2)
    public void getPet() {
        String petId = "4758239";

        String token = given()
                .contentType("application/json")
                .log().all()
                .when()
                .get(uri + "/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Atenas"))
                .body("category.name", is("AT49FJE0L"))
                .extract()
                .path("category.name");

        System.out.println("O token é " + token);

    }

    @Test(priority = 3)
    public void setPet() throws IOException {
        String jsonBody = readJson("C:/PetStore/src/test/resources/data/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
                .when()
                .put(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Atenas"))
                .body("status", is("sold"));
    }
}
