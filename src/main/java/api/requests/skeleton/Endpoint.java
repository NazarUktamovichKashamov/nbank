package api.requests.skeleton;

import api.models.*;
import lombok.Getter;


@Getter

public enum Endpoint {

    C2C_ENDPOINT(
            "/api/v1/accounts/transfer",
            C2CRequestModel.class,
            C2CResponseModel.class
    ),

    CHANGE_NAME_ENDPOINT(
            "/api/v1/customer/profile",
            ChangeNameRequestModel.class,
            ChangeNameResponseModel.class
    ),

    USER_DEPOSIT_ENDPOINT(
            "/api/v1/accounts/deposit",
            DepositRequestModel.class,
            DepositResponseModel.class
    ),
    ADMIN_USER(
            "/admin/users",
            CreateUserRequestModel.class,
            CreateUserResponseModel.class
    ),
    LOGIN_ENDPOINT(
            "/api/v1/auth/login",
            C2CRequestModel.class,
            C2CResponseModel.class
    ),
    ACCOUNTS(
            "/api/v1/accounts",
            BaseModel.class,
            CreateAccountResponseModel.class
    ),

    CUSTOMER_ACCOUNTS(
            "/api/v1/customer/accounts",
            BaseModel.class,
            CreateAccountResponseModel.class
    ),
    ACCOUNT_DEPOSIT(
            "/api/v1/accounts/deposit",
            DepositRequestModel.class,
            DepositResponseModel.class
    ),

    ACCOUNT_TRANSFER(
            "/api/v1/accounts/transfer",
            TransferRequestModel.class,
            TransferResponseModel.class
    ),

    TRANSFER_WITH_FRAUD_CHECK(
            "/api/v1/accounts/transfer-with-fraud-check",
            TransferRequestModel.class,
            TransferResponseModel.class
    ),

    FRAUD_CHECK_STATUS(
            "/api/v1/accounts/fraud-check/{transactionId}",
            BaseModel.class,
            FraudCheckResponseModel.class
    ),
    ACCOUNTS_ENDPOINT(
            "/api/v1/auth/login",
            C2CRequestModel.class,
            C2CResponseModel.class
    );

    Endpoint(String url, Class<? extends BaseModel> requestModel, Class<? extends BaseModel> responseModel) {
        this.url = url;
        this.requestModel = requestModel;
        this.responseModel = responseModel;
    }

    public String getUrl() {
        return url;
    }

    public Class<? extends BaseModel> getRequestModel() {
        return requestModel;
    }

    public Class<? extends BaseModel> getResponseModel() {
        return responseModel;
    }

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
}
