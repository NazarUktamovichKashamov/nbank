package api.Iteration2;

import api.models.*;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
//import api.requests.steps.AccountSteps;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import common.annotations.FraudCheckMock;
import common.extensions.TImingExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({TImingExtension.class, FraudCheckWireMockExtension.class})
public class TransferWithFraudCheckTest extends BaseTest {

    private CreateUserRequestModel user1;
    private CreateUserRequestModel user2;
    private CreateAccountResponseModel account1;
    private CreateAccountResponseModel account2;
    private DepositResponseModel depositResponse;
    private TransferResponseModel transferResponseModel;

    @BeforeEach
    public void setupTest() {
        this.softly = new SoftAssertions();
    }
/*
    @Test
    @FraudCheckMock(
            status = "SUCCESS",
            decision = "APPROVED",
            riskScore = 0.2,
            reason = "Low risk transaction",
            requiresManualReview = false,
            additionalVerificationRequired = false
    )

    public void testTransferWithFraudCheck() {
        user1 = AdminSteps.createUser();

        AccountSteps accountSteps1 = new AccountSteps(user1.getUsername(), user1.getPassword());
        account1 = accountSteps1.createAccount();

        long depositAmount = (long) (Math.random() * 4999.9 + 0.1);
        depositResponse = accountSteps1.depositToAccount(account1.getId(), depositAmount);

        user2 = AdminSteps.createUser();
        AccountSteps accountSteps2 = new AccountSteps(user2.getUsername(), user2.getPassword());
        account2 = accountSteps2.createAccount();

        double transferAmount = Math.random() * (depositAmount - 0.1) + 0.1;
        transferResponseModel = accountSteps1.transferWithFraudCheck(
                (long) account1.getId(),
                (long) account2.getId(),
                transferAmount
        );

        softly.assertThat(transferResponseModel).isNotNull();


        TransferResponseModel expectedResponse = TransferResponseModel.builder()
                .status("APPROVED")
                .message("Transfer approved and processed immediately")
                .amount(transferAmount)
                .senderAccountId(account1.getId())
                .receiverAccountId(account2.getId())
                .fraudRiskScore(0.2)
                .fraudReason("Low risk transaction")
                .requiresManualReview(false)
                .requiresVerification(false)
                .build();

        ModelAssertions.assertThatModels(expectedResponse, transferResponseModel).match();
    }

 */

    @Test
    @FraudCheckMock(
            status = "SUCCESS",
            decision = "APPROVED",
            riskScore = 0.2,
            reason = "Low risk transaction",
            requiresManualReview = false,
            additionalVerificationRequired = false
    )
    public void testt(){
        TransferRequestModel transferRequestModel = TransferRequestModel.builder()
                .senderAccountId(4)
                .receiverAccountId(2)
                .amount(2)
                .description("bla bla")
                .build();

        TransferResponseModel transferResponseModel = new ValidatedCrudRequester<TransferResponseModel>(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.requestReturnsOK(),
                Endpoint.TRANSFER_WITH_FRAUD_CHECK
        )
                .post(transferRequestModel);
    }

    @AfterEach
    public void afterTest() {
        softly.assertAll();
    }
}