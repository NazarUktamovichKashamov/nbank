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

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class C2CTransferTest {
    @BeforeAll
    public static void setupRestAssured() {
        RestAssured.filters(
                List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()));
    }


    @Test
    public void positiveC2CTest(){
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                            {
                              "senderAccountId": 1,
                              "receiverAccountId": 2,
                              "amount": 50
                            }
              """)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .post("http://localhost:4111/api/v1/accounts/transfer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("message", equalTo("Transfer successful"))
                .body("receiverAccountId", equalTo(2))
                .body("senderAccountId", equalTo(1));
        }


    @Test
    public void negativeTransferToNotExistingClientTest(){
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                            {
                              "senderAccountId": 1,
                              "receiverAccountId": 999999,
                              "amount": 50
                            }
              """)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .post("http://localhost:4111/api/v1/accounts/transfer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.equalTo("Invalid transfer: insufficient funds or invalid accounts"));
    }


    @Test
    public void negativeTransferFromNotExistingClientTest(){
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                            {
                              "senderAccountId": 99999,
                              "receiverAccountId": 2,
                              "amount": 50
                            }
              """)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .post("http://localhost:4111/api/v1/accounts/transfer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.equalTo("Unauthorized access to account"));
    }


    @Test
    public void TransferMe2MeTest(){
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                            {
                              "senderAccountId": 1,
                              "receiverAccountId": 1,
                              "amount": 50
                            }
              """)
                .header("Authorization", "Basic a2F0ZTE5OTg6S2F0ZTE5OTgk")
                .post("http://localhost:4111/api/v1/accounts/transfer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("message", equalTo("Transfer successful"))
                .body("receiverAccountId", equalTo(1))
                .body("senderAccountId", equalTo(1));
    }
}
