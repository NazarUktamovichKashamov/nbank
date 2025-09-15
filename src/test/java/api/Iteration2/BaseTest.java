package api.Iteration2;

import api.models.AccountsResponseModel;
import api.models.DepositRequestModel;
import api.specs.RequestSpecs;
import common.extensions.TImingExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;


@ExtendWith(TImingExtension.class)
public class BaseTest {
    protected SoftAssertions softly;

    @BeforeEach
    public void setupTest(){
        this.softly = new SoftAssertions();
    }

    @AfterEach
    public void afterTest(){
        softly.assertAll();
    }



    public double findBalance(long accountId){
        AccountsResponseModel[] returnJsonAsObject = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);
       return Arrays.stream(returnJsonAsObject)
                .filter(a -> a.getId() == accountId)
                .mapToDouble(AccountsResponseModel::getBalance)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account not found"));
    }

    public boolean isDepositExists(int userAccountId, double amount){
        AccountsResponseModel[] returnJsonAsObject = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);
        AccountsResponseModel acc1 = Arrays.stream(returnJsonAsObject)
                .filter(a -> a.getId() == userAccountId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account with id not found"));

        return acc1.getTransactions().stream()
                .map(t -> (Map<String, Object>) t)
                .anyMatch(m -> "DEPOSIT".equals(m.get("type"))
                        && ((Double) m.get("amount")) == amount);


    }

    public boolean isC2CExists(int userAccountId, double amount){
        AccountsResponseModel[] returnJsonAsObject = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);
        AccountsResponseModel acc1 = Arrays.stream(returnJsonAsObject)
                .filter(a -> a.getId() == userAccountId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account with id not found"));

        return acc1.getTransactions().stream()
                .map(t -> (Map<String, Object>) t)
                .anyMatch(m -> "TRANSFER_OUT".equals(m.get("type"))
                        && ((Double) m.get("amount")) == amount);
    }
}
