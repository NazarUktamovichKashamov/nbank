package ui;

import Requests.skeleton.Endpoint;
import Requests.skeleton.requesters.CrudRequester;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import generators.RandomData;
import models.ChangeNameRequestModel;
import models.LoginRequestModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeNameUITest {
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
    public void userCanChangeNameWithValidData(){
        ChangeNameRequestModel user = ChangeNameRequestModel.builder().name(RandomData.CreateValidName()).build();
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
        $(Selectors.byText("@")).click();
        $(Selectors.byAttribute("placeholder", "Enter new name")).clear();
        $(Selectors.byAttribute("placeholder", "Enter new name")).sendKeys(user.getName());
        $(Selectors.byAttribute("class", "btn btn-primary mt-3")).click();

        Alert alert = switchTo().alert();
        assertEquals("✅ Name updated successfully!", alert.getText());
        alert.accept();

        $(Selectors.byText("\uD83C\uDFE0 Home")).click();
        $(Selectors.byText(user.getName())).shouldBe(Condition.visible);
    }

    @Test
    public void userCanChangeNameWithInvalidData(){
        ChangeNameRequestModel user = ChangeNameRequestModel.builder().name(RandomData.CreateSingleWordName()).build();
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
        $(Selectors.byText("@")).click();
        $(Selectors.byAttribute("placeholder", "Enter new name")).clear();
        $(Selectors.byAttribute("placeholder", "Enter new name")).sendKeys(user.getName());
        $(Selectors.byAttribute("class", "btn btn-primary mt-3")).click();

        Alert alert = switchTo().alert();
        assertEquals("Name must contain two words with letters only", alert.getText());
        alert.accept();

        $(Selectors.byText("\uD83C\uDFE0 Home")).click();
        $(Selectors.byText(user.getName())).shouldNotBe(Condition.visible);
    }
}
