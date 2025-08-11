package Iteration2;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import models.DepositRequestModel;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;

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
            DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                    .id(1)
                    .balance(1000)
                    .build();
            new Requests.UserDepositRequest(
                    RequestSpecs.userOneAuthSpec(),
                    ResponseSpecs.DepositPositiveRequest())
                    .post(depositRequestModel);
    }


    @Test
    public void DepositZeroAmountTest() {
        DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                .id(1)
                .balance(0)
                .build();
        new Requests.UserDepositRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.DepositBadRequest())
                .post(depositRequestModel);
    }


    @Test
    public void DepositNegativeAmountTest() {
        DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                .id(1)
                .balance(-100)
                .build();
        new Requests.UserDepositRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.DepositBadRequest())
                .post(depositRequestModel);
    }
}
