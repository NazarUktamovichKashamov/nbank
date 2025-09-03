package api.Iteration2;

import api.Requests.skeleton.Endpoint;
import api.Requests.skeleton.requesters.CrudRequester;
import api.Requests.skeleton.requesters.ValidatedCrudRequester;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import api.models.C2CRequestModel;
import api.models.C2CResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.assertj.core.api.SoftAssertions;


import java.util.List;

public class C2CTransferTest {
    @BeforeAll
    public static void setupRestAssured() {
        RestAssured.filters(
                List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()));
    }

    @BeforeAll
    public static void makeSoftAsserts(){
        SoftAssertions softly = new SoftAssertions();
    }


    @Test
    public void positiveC2CTest(){
        SoftAssertions softly = new SoftAssertions();
        C2CRequestModel c2CRequestModel = C2CRequestModel.builder()
                .amount(1)
                .senderAccountId(1)
                .receiverAccountId(2)
                .build();

        C2CResponseModel c2CResponseModel = new ValidatedCrudRequester<C2CResponseModel>
                (RequestSpecs.userOneAuthSpec(),
                        ResponseSpecs.C2CWasSuccessfullyDone(),
                        Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
        softly.assertThat(c2CRequestModel.getSenderAccountId()).isEqualTo(c2CResponseModel.getSenderAccountId());
        softly.assertThat(c2CRequestModel.getReceiverAccountId()).isEqualTo(c2CResponseModel.getReceiverAccountId());

        }

    @Test
    public void negativeTransferToNotExistingClientTest(){
        C2CRequestModel c2CRequestModel = C2CRequestModel.builder()
                .amount(1)
                .receiverAccountId(999999999)
                .senderAccountId(1)
                .build();

        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CBadRequest(),
                Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
    }


    @Test
    public void negativeTransferFromNotExistingClientTest() {
        C2CRequestModel c2CRequestModel = C2CRequestModel.builder()
                .amount(1)
                .receiverAccountId(2)
                .senderAccountId(999999999)
                .build();

        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CForbidden(),
                Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
    }


    @Test
    public void TransferMe2MeTest(){
        SoftAssertions softly = new SoftAssertions();
        C2CRequestModel c2CRequestModel = C2CRequestModel.builder()
                .amount(1)
                .senderAccountId(1)
                .receiverAccountId(1)
                .build();

        C2CResponseModel c2CResponseModel = new ValidatedCrudRequester<C2CResponseModel>
                (RequestSpecs.userOneAuthSpec(),
                        ResponseSpecs.C2CWasSuccessfullyDone(),
                        Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
        softly.assertThat(c2CRequestModel.getSenderAccountId()).isEqualTo(c2CResponseModel.getSenderAccountId());
        softly.assertThat(c2CRequestModel.getReceiverAccountId()).isEqualTo(c2CResponseModel.getReceiverAccountId());
    }
}
