package specs;

import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecs {
    private ResponseSpecs(){}

    private static ResponseSpecBuilder defaultResponseBuilder(){
        return new ResponseSpecBuilder();
    }

    public static ResponseSpecification C2CWasSuccessfullyDone(){
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectBody("message", equalTo("Transfer successful"))
                .build();
    }

    public static ResponseSpecification C2CForbidden(){
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_FORBIDDEN)
                .expectBody(Matchers.equalTo("Unauthorized access to account"))
                .build();
    }

    public static ResponseSpecification C2CBadRequest(){
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.equalTo("Invalid transfer: insufficient funds or invalid accounts"))
                .build();

    }

    public static ResponseSpecification DepositPositiveRequest(){
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectBody("accountNumber", equalTo("ACC1"))
                .build();
    }

    public static ResponseSpecification DepositBadRequest(){
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.equalTo("Invalid account or amount"))
                .build();
    }

    public static ResponseSpecification ValidUsernameRequest(){
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectBody("message", equalTo("Profile updated successfully"))
                .build();
    }

    public static ResponseSpecification InvalidUsernameRequest(){
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.equalTo("Name must contain two words with letters only"))
                .build();
    }
}
