package Iteration2;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import models.C2CRequestModel;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;


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
        C2CRequestModel c2CRequestModel = C2CRequestModel.builder()
                .amount(1)
                .senderAccountId(1)
                .receiverAccountId(2)
                .build();

        new Requests.C2CRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CWasSuccessfullyDone())
                .post(c2CRequestModel);

        }

    @Test
    public void negativeTransferToNotExistingClientTest(){
        C2CRequestModel c2CRequestModel = C2CRequestModel.builder()
                .amount(1)
                .receiverAccountId(999999999)
                .senderAccountId(1)
                .build();

        new Requests.C2CRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CBadRequest())
                .post(c2CRequestModel);
    }


    @Test
    public void negativeTransferFromNotExistingClientTest() {
        C2CRequestModel c2CRequestModel = C2CRequestModel.builder()
                .amount(1)
                .receiverAccountId(2)
                .senderAccountId(999999999)
                .build();

        new Requests.C2CRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CForbidden())
                .post(c2CRequestModel);
    }


    @Test
    public void TransferMe2MeTest(){
        C2CRequestModel c2CRequestModel = C2CRequestModel.builder()
                .amount(1)
                .senderAccountId(1)
                .receiverAccountId(1)
                .build();

        new Requests.C2CRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CWasSuccessfullyDone())
                .post(c2CRequestModel);
    }
}
