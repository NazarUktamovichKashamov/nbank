package api.Iteration2;

import api.dao.TransactionDao;
import api.dao.comparison.DaoAndModelAssertions;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.models.DepositRequestModel;
import api.models.DepositResponseModel;
import api.requests.steps.DataBaseSteps;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

import static org.junit.jupiter.api.Assertions.assertNull;


public class DepositToAccountTest extends BaseTest{

        @Test
        public void positiveDepositTest() {
            SoftAssertions softly = new SoftAssertions();
            DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                    .id(7)
                    .balance(1000)
                    .build();
            DepositResponseModel depositResponseModel = new ValidatedCrudRequester<DepositResponseModel>
                    (RequestSpecs.userOneAuthSpec(),
                            ResponseSpecs.DepositPositiveRequest(),
                            Endpoint.USER_DEPOSIT_ENDPOINT)
                    .post(depositRequestModel);
            softly.assertThat(depositRequestModel.getId()).isEqualTo(depositResponseModel.getId());

            TransactionDao transactionDao = DataBaseSteps.getTransactionByReceiverAccount(depositRequestModel.getId());
            DaoAndModelAssertions.assertThat(depositResponseModel, transactionDao).match();
    }


    @Test
    public void DepositZeroAmountTest() {
        DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                .id(6)
                .balance(0)
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.DepositBadRequest(),
                Endpoint.USER_DEPOSIT_ENDPOINT)
                .post(depositRequestModel);

        assertNull(DataBaseSteps.getTransactionByReceiverAccount(depositRequestModel.getId()));

    }


    @Test
    public void DepositNegativeAmountTest() {
        DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                .id(4)
                .balance(-100)
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.DepositBadRequest(),
                Endpoint.USER_DEPOSIT_ENDPOINT)
                .post(depositRequestModel);

        assertNull(DataBaseSteps.getTransactionByReceiverAccount(depositRequestModel.getId()));
    }
}
