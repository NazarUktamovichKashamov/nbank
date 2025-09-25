package api.Iteration2;

import api.dao.TransactionDao;
import api.dao.comparison.DaoAndModelAssertions;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.models.comparison.ModelAssertions;
import api.models.C2CRequestModel;
import api.models.C2CResponseModel;
import api.requests.steps.DataBaseSteps;
import org.junit.jupiter.api.Test;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;


import static org.junit.jupiter.api.Assertions.*;

public class C2CTransferTest extends BaseTest {

    @Test
    public void positiveC2CTest() {
        C2CRequestModel c2CRequestModel = C2CRequestModel.generateC2CWithValidNameTest();

        C2CResponseModel c2CResponseModel = new ValidatedCrudRequester<C2CResponseModel>
                (RequestSpecs.userOneAuthSpec(),
                        ResponseSpecs.C2CWasSuccessfullyDone(),
                        Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);

        assertTrue(isC2CExists(c2CRequestModel.getSenderAccountId(), c2CRequestModel.getAmount()));
        ModelAssertions.assertThatModels(c2CRequestModel, c2CResponseModel);

        TransactionDao transactionDao = DataBaseSteps.getTransactionBySenderAccount(c2CRequestModel.getSenderAccountId());
        DaoAndModelAssertions.assertThat(c2CResponseModel, transactionDao).match();
    }

    @Test
    public void negativeTransferToNotExistingClientTest() {
        C2CRequestModel c2CRequestModel = C2CRequestModel.generateC2CWithValidNameTest();
        c2CRequestModel.setReceiverAccountId(999999999);

        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.C2CBadRequest(),
                Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
        assertFalse(isC2CExists(c2CRequestModel.getSenderAccountId(), c2CRequestModel.getAmount()));

        assertNull(DataBaseSteps.getTransactionByReceiverAccount(c2CRequestModel.getReceiverAccountId()));
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
        assertThrows(AssertionError.class, () -> assertFalse(isC2CExists(c2CRequestModel.getSenderAccountId(), c2CRequestModel.getAmount())));
        assertNull(DataBaseSteps.getTransactionBySenderAccount(c2CRequestModel.getSenderAccountId()));
    }


    @Test
    public void TransferMe2MeTest() {
        C2CRequestModel c2CRequestModel = C2CRequestModel.generateC2CWithValidNameTest();

        C2CResponseModel c2CResponseModel = new ValidatedCrudRequester<C2CResponseModel>
                (RequestSpecs.userOneAuthSpec(),
                        ResponseSpecs.C2CWasSuccessfullyDone(),
                        Endpoint.C2C_ENDPOINT)
                .post(c2CRequestModel);
        ModelAssertions.assertThatModels(c2CRequestModel, c2CResponseModel);

        assertTrue(isC2CExists(c2CRequestModel.getSenderAccountId(), c2CRequestModel.getAmount()));

        TransactionDao transactionDao = DataBaseSteps.getTransactionBySenderAccount(c2CRequestModel.getSenderAccountId());
        DaoAndModelAssertions.assertThat(c2CResponseModel, transactionDao).match();
    }
}
