package ui;

import Requests.skeleton.Endpoint;
import Requests.skeleton.requesters.CrudRequester;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import models.AccountsResponseModel;
import models.C2CRequestModel;
import models.LoginRequestModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.Arrays;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class C2CTransferUITest {
    @BeforeAll
    public static void setupSelenoid(){
        Configuration.baseUrl = "http://localhost:3000";
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true, "enableLog", true)
        );
    }

    @Test
    public void UserCanSendC2CToAnotherUserTest(){

        C2CRequestModel transfer = C2CRequestModel.builder().senderAccountId(1).receiverAccountId(2).amount(2).build();
        String userAuthHeader = userAuthHeader = new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.BlankLogin(),
                Endpoint.LOGIN_ENDPOINT)

                .post(LoginRequestModel.builder().username("Nazar2004").password("Nazar2004!").build())
                .extract()
                .header("Authorization");

        Selenide.open("/");

        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", userAuthHeader);

        AccountsResponseModel[] findInitialBalance = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);

        double initialBalance = Arrays.stream(findInitialBalance)
                .filter(a -> a.getId() == 1)
                .mapToDouble(AccountsResponseModel::getBalance)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account not found"));

        Selenide.open("/dashboard");

        $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();
        $(Selectors.byText("-- Choose an account --")).click();
        $(Selectors.byText("ACC1")).click();
        $(Selectors.byAttribute("placeholder", "Enter recipient name")).sendKeys("Unnecessary");
        $(Selectors.byAttribute("placeholder", "Enter recipient account number")).sendKeys("ACC"+transfer.getReceiverAccountId());
        $(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys(String.valueOf(transfer.getAmount()));
        $(Selectors.byText("Confirm details are correct")).click();
        $(Selectors.byText("\uD83D\uDE80 Send Transfer")).click();

        Alert alert = switchTo().alert();
        assertEquals("✅ Successfully transferred $"+ transfer.getAmount() +" to account ACC2!", alert.getText());
        alert.accept();


        AccountsResponseModel[] accounts = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);

        double afterBalance = Arrays.stream(accounts)
                .filter(a -> a.getId() == 1)
                .mapToDouble(AccountsResponseModel::getBalance)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account not found"));

        assertThat(afterBalance - initialBalance == transfer.getAmount());


    }

    @Test
    public void UserCantSendMe2MeWithInvalidNameTest(){
        C2CRequestModel transfer = C2CRequestModel.builder().senderAccountId(1).receiverAccountId(2).amount(2).build();
        String userAuthHeader = userAuthHeader = new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.BlankLogin(),
                Endpoint.LOGIN_ENDPOINT)

                .post(LoginRequestModel.builder().username("Nazar2004").password("Nazar2004!").build())
                .extract()
                .header("Authorization");
        Selenide.open("/");
        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", userAuthHeader);

        AccountsResponseModel[] findInitialBalance = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);

        double initialBalance = Arrays.stream(findInitialBalance)
                .filter(a -> a.getId() == 1)
                .mapToDouble(AccountsResponseModel::getBalance)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account not found"));


        Selenide.open("/dashboard");
        $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();
        $(Selectors.byText("-- Choose an account --")).click();
        $(Selectors.byText("ACC1")).click();
        $(Selectors.byAttribute("placeholder", "Enter recipient name")).sendKeys("Unnecessary");
        $(Selectors.byAttribute("placeholder", "Enter recipient account number")).sendKeys("ACC"+transfer.getSenderAccountId());
        $(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys(String.valueOf(transfer.getAmount()));
        $(Selectors.byText("Confirm details are correct")).click();
        $(Selectors.byText("\uD83D\uDE80 Send Transfer")).click();

        Alert alert = switchTo().alert();
        assertEquals("❌ The recipient name does not match the registered name.", alert.getText());
        alert.accept();


        AccountsResponseModel[] accounts = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);

        double afterBalance = Arrays.stream(accounts)
                .filter(a -> a.getId() == 1)
                .mapToDouble(AccountsResponseModel::getBalance)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account not found"));

        assertEquals(initialBalance, afterBalance);
    }
}
