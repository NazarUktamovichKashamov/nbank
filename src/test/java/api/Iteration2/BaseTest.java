package api.Iteration2;

import api.models.AccountsResponseModel;
import api.specs.RequestSpecs;
import common.extensions.TImingExtension;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;


@ExtendWith(TImingExtension.class)
public class BaseTest {
    protected SoftAssertions softly;

    @BeforeEach
    public void setupTest() {
        this.softly = new SoftAssertions();
    }

    @BeforeEach
    void setUp() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterEach
    public void afterTest() {
        softly.assertAll();
    }


    public double findBalance(int accountId) {
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

    public boolean isDepositExists(int userAccountId, double amount) {
        AccountsResponseModel[] returnJsonAsObject = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);
        AccountsResponseModel acc1 = Arrays.stream(returnJsonAsObject)
                .filter(a -> Objects.equals(a.getId(), userAccountId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account with id not found"));

        return acc1.getTransactions().stream()
                .map(t -> (Map<String, Object>) t)
                .anyMatch(m -> "DEPOSIT".equals(m.get("type"))
                        && ((Double) m.get("amount")) == amount);


    }

    public boolean isC2CExists(int userAccountId, double amount) {
        AccountsResponseModel[] returnJsonAsObject = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);
        AccountsResponseModel acc1 = Arrays.stream(returnJsonAsObject)
                .filter(a -> Objects.equals(a.getId(), userAccountId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account with id not found"));

        return acc1.getTransactions().stream()
                .map(t -> (Map<String, Object>) t)
                .anyMatch(m -> "TRANSFER_OUT".equals(m.get("type"))
                        && ((Double) m.get("amount")) == amount);
    }
}
