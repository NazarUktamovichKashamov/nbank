package ui;

import Requests.skeleton.Endpoint;
import Requests.skeleton.requesters.CrudRequester;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import models.AccountsResponseModel;
import models.DepositRequestModel;
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
import static org.junit.jupiter.api.Assertions.*;

public class DepositUITest {
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
    public void userCanDeposit(){
        DepositRequestModel user = DepositRequestModel.builder().id(1).balance(100).build();
        String userAuthHeader = userAuthHeader = new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.BlankLogin(),
                Endpoint.LOGIN_ENDPOINT)

                .post(LoginRequestModel.builder().username("Nazar2004").password("Nazar2004!").build())
                .extract()
                .header("Authorization");
        Selenide.open("/");
        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", userAuthHeader);
        Selenide.open("/dashboard");

        $("#root > div > div > button:nth-child(1)").click();
        $(Selectors.byAttribute("class", "form-control account-selector")).selectOption(1);
        $(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys(String.valueOf(user.getBalance()));
        $("#root > div > button").click();

        Alert alert = switchTo().alert();

        assertEquals("✅ Successfully deposited $"+user.getBalance()+" to account ACC1!", alert.getText());
        alert.accept();

        AccountsResponseModel[] accounts = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);

        AccountsResponseModel acc1 = Arrays.stream(accounts)
                .filter(a -> a.getId() == 1)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account with id=1 not found"));

        boolean DepositExists = acc1.getTransactions().stream()
                .map(t -> (Map<String, Object>) t)
                .anyMatch(m -> "DEPOSIT".equals(m.get("type"))
                        && ((Double) m.get("amount")) == user.getBalance());

        assertTrue(DepositExists);
    }


    @Test
    public void userCanNotDepositNegativeNumber(){
        DepositRequestModel user = DepositRequestModel.builder().id(1).balance(-10).build();
        String userAuthHeader = userAuthHeader = new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.BlankLogin(),
                Endpoint.LOGIN_ENDPOINT)

                .post(LoginRequestModel.builder().username("Nazar2004").password("Nazar2004!").build())
                .extract()
                .header("Authorization");
        Selenide.open("/");
        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", userAuthHeader);
        Selenide.open("/dashboard");

        $("#root > div > div > button:nth-child(1)").click();
        $(Selectors.byAttribute("class", "form-control account-selector")).selectOption(1);
        $(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys(String.valueOf(user.getBalance()));
        $("#root > div > button").click();

        Alert alert = switchTo().alert();

        assertEquals("❌ Please enter a valid amount.", alert.getText());
        alert.accept();

        // API Check
        AccountsResponseModel[] accounts = given()
                .spec(RequestSpecs.userOneAuthSpec())
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .assertThat()
                .extract().as(AccountsResponseModel[].class);

        AccountsResponseModel acc1 = Arrays.stream(accounts)
                .filter(a -> a.getId() == 1)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Account with id=1 not found"));

        boolean negativeDepositExists = acc1.getTransactions().stream()
                .map(t -> (Map<String, Object>) t)
                .anyMatch(m -> "DEPOSIT".equals(m.get("type"))
                        && ((Double) m.get("amount")) == -10.0);

        assertFalse(negativeDepositExists);

    }
}
