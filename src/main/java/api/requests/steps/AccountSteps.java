/*
package api.requests.steps;

import api.models.*;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import common.helps.StepLogger;

public class AccountSteps {
    private String username;
    private String password;

    public AccountSteps(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CreateAccountResponseModel createAccount() {
        return StepLogger.log("User " + username + " creates account", () -> {
            return new ValidatedCrudRequester<CreateAccountResponseModel>(
                    RequestSpecs.getUserAuthHeader(username, password),
                    ResponseSpecs.entityWasCreated(),
                    Endpoint.ACCOUNTS)
                    .post(null);
        });
    }

    public DepositResponseModel depositToAccount(int accountId, long amount) {
        return StepLogger.log("User " + username + " deposits " + amount + " to account " + accountId, () -> {
            DepositRequestModel depositRequest = DepositRequestModel.builder()
                    .id(accountId)
                    .amount(amount)
                    .description("Test deposit")
                    .build();

            return new ValidatedCrudRequester<DepositResponseModel>(
                    RequestSpecs.getUserAuthHeader(username, password),
                    ResponseSpecs.requestReturnsOK(),
                    Endpoint.ACCOUNT_DEPOSIT).post(depositRequest);
        });
    }

    public TransferResponseModel transferWithFraudCheck(Long senderAccountId, Long receiverAccountId, double amount) {
        return StepLogger.log("User " + username + " transfers " + amount + " to " + receiverAccountId + " with fraud check", () -> {
            TransferRequestModel transferRequest = TransferRequestModel.builder()
                    .senderAccountId(senderAccountId)
                    .receiverAccountId(receiverAccountId)
                    .amount(amount)
                    .description("Test transfer with fraud check")
                    .build();

            return new ValidatedCrudRequester<TransferResponseModel>(
                    RequestSpecs.getUserAuthHeader(username, password),
                    ResponseSpecs.requestReturnsOK(),
                    Endpoint.TRANSFER_WITH_FRAUD_CHECK)
                    .post(transferRequest);
        });
    }
}
 */