package api.Requests.skeleton;

import api.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import api.models.*;

@AllArgsConstructor
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
    LOGIN_ENDPOINT(
            "/api/v1/auth/login",
            C2CRequestModel.class,
            C2CResponseModel.class
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
}
