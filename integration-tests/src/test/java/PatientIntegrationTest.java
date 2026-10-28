import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken() {
        String email = requiredEnvironmentVariable("TEST_AUTH_EMAIL");
        String password = requiredEnvironmentVariable("TEST_AUTH_PASSWORD");
        String token = given().contentType("application/json")
                .body(Map.of("email", email, "password", password)).when()
                .post("/auth/login").then().statusCode(200).extract().jsonPath().get("token");

        given().header("Authorization", "Bearer " + token).when().get("/api/patients").then()
                .statusCode(200).body("patients", notNullValue());
    }

    private static String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(name + " must be set to run integration tests");
        }
        return value;
    }
}
