package api.Iteration2;

import api.Requests.skeleton.Endpoint;
import api.Requests.skeleton.requesters.CrudRequester;
import api.Requests.skeleton.requesters.ValidatedCrudRequester;
import api.models.comparison.ModelAssertions;
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

import static org.junit.jupiter.api.Assertions.*;

public class C2CTransferTest extends BaseTest {

    @Test
    public void positiveC2CTest(){
        C2CRequestModel c2CRequestModel = C2CRequestModel.generateC2CWithValidNameTest();

        C2CResponseModel c2CResponseModel = new ValidatedCrudRequester<C2CResponseModel>
                (RequestSpecs.userOneAuthSpec(),
                        ResponseSpecs.C2CWasSuccessfullyDone(),
                        Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);

        assertTrue(isC2CExists(c2CRequestModel.getSenderAccountId(), c2CRequestModel.getAmount()));
        ModelAssertions.assertThatModels(c2CRequestModel, c2CResponseModel);
        }

    @Test
    public void negativeTransferToNotExistingClientTest(){
        C2CRequestModel c2CRequestModel = C2CRequestModel.generateC2CWithValidNameTest();
        c2CRequestModel.setReceiverAccountId(999999999);

        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CBadRequest(),
                Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
        assertFalse(isC2CExists(c2CRequestModel.getSenderAccountId(), c2CRequestModel.getAmount()));
    }


    @Test
    public void negativeTransferFromNotExistingClientTest() {
        C2CRequestModel c2CRequestModel = C2CRequestModel.generateC2CWithValidNameTest();
        c2CRequestModel.setSenderAccountId(9999999);

        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CForbidden(),
                Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
        assertThrows(AssertionError.class, ()-> assertFalse(isC2CExists(c2CRequestModel.getSenderAccountId(), c2CRequestModel.getAmount())));

    }


    @Test
    public void TransferMe2MeTest(){
        C2CRequestModel c2CRequestModel = C2CRequestModel.generateC2CWithValidNameTest();

        C2CResponseModel c2CResponseModel = new ValidatedCrudRequester<C2CResponseModel>
                (RequestSpecs.userOneAuthSpec(),
                        ResponseSpecs.C2CWasSuccessfullyDone(),
                        Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
        ModelAssertions.assertThatModels(c2CRequestModel, c2CResponseModel);
        assertTrue(isC2CExists(c2CRequestModel.getSenderAccountId(), c2CRequestModel.getAmount()));

    }
}
