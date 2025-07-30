package Iteration2;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ChangeNameTest {
    @BeforeAll
    public static void setupRestAssured() {
        RestAssured.filters(
                List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()));
    }


    @CsvSource({
        "New Name"
    })
    @ParameterizedTest
    public void positiveChangeNameTest(String newUsername){
        String requestBody = String.format(
                """
                {
                "name": "%s"
                }
               
                """, newUsername);
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .put("http://localhost:4111/api/v1/customer/profile")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("message", Matchers.equalTo("Profile updated successfully"))
                .body("customer.name", Matchers.equalTo(newUsername));
    }
    @CsvSource({
            "Ne"
    })
    @ParameterizedTest
    public void negativeUsernameOneWordTest(String newUsername){
        String requestBody = String.format(
                """
                {
                "name": "%s"
                }
               
                """, newUsername);
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .put("http://localhost:4111/api/v1/customer/profile")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.equalTo("Name must contain two words with letters only"));
    }
    @CsvSource({
            "Ne W Name"
    })
    @ParameterizedTest
    public void negativeUsername3WordsTest(String newUsername){
        String requestBody = String.format(
                """
                {
                "name": "%s"
                }
               
                """, newUsername);
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .put("http://localhost:4111/api/v1/customer/profile")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.equalTo("Name must contain two words with letters only"));
    }
    @CsvSource({
            "Ne4w Na5me"
    })
    @ParameterizedTest
    public void negativeUsernameWithDigitsTest(String newUsername){
        String requestBody = String.format(
                """
                {
                "name": "%s"
                }
               
                """, newUsername);
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .put("http://localhost:4111/api/v1/customer/profile")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.equalTo("Name must contain two words with letters only"));
    }
}
