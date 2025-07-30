package Iteration2;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DepositToAccountTest {
        @BeforeAll
        public static void setupRestAssured() {
            RestAssured.filters(
                    List.of(new RequestLoggingFilter(),
                            new ResponseLoggingFilter()));
        }

        @Test
        public void positiveDepositTest() {
            given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body("""
                            {
              "id": 1,
              "balance": 100
              }
              """)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .post("http://localhost:4111/api/v1/accounts/deposit")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("accountNumber", equalTo("ACC1"))
                .body(("balance"), equalTo("1100.0"));
    }
    @Test
    public void DepositZeroAmountTest() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                            {
              "id": 1,
              "balance": 0
              }
              """)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .post("http://localhost:4111/api/v1/accounts/deposit")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.equalTo("Invalid account or amount"));
    }
    @Test
    public void DepositNegativeAmountTest() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                            {
              "id": 1,
              "balance": -1
              }
              """)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .post("http://localhost:4111/api/v1/accounts/deposit")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.equalTo("Invalid account or amount"));
    }
}
