import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class AuthIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";

    }

    @Test
    public void shouldReturnOKWithValidToken() {
        given().contentType("application/json")
                .body(Map.of("email", requiredEnvironmentVariable("TEST_AUTH_EMAIL"),
                        "password", requiredEnvironmentVariable("TEST_AUTH_PASSWORD"))).when()
                .post("/auth/login").then().statusCode(200).body("token", notNullValue());
    }


    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {
        given().contentType("application/json")
                .body(Map.of("email", "invalid-user@example.test",
                        "password", "invalid-password")).when()
                .post("/auth/login").then().statusCode(401);
    }

    private static String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(name + " must be set to run integration tests");
        }
        return value;
    }
}
