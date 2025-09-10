package ui.pages;

import com.codeborne.selenide.Condition;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import ui.elements.WelcomeMessage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Getter

public class DashboardPage extends BasePage<DashboardPage>{

    private SelenideElement transfer = $(Selectors.byText("\uD83D\uDD04 Make a Transfer"));
    private SelenideElement deposit = $(Selectors.byText("\uD83D\uDCB0 Deposit Money"));
    private SelenideElement createAccount = $(Selectors.byText("➕ Create New Account"));
    private SelenideElement logout = $(Selectors.byText("\uD83D\uDEAA Logout"));
    private SelenideElement welcomeText = $(".welcome-text");

    @Override
    public String url() {
        return "/dashboard";
    }

    public WelcomeMessage welcomeMessage() {
        return new WelcomeMessage(welcomeText);
    }
    public DashboardPage waitForUsernameToLoad(String expectedName) {
        $(Selectors.byCssSelector(".welcome-text span"))
                .shouldHave(Condition.text(expectedName), Duration.ofSeconds(15));
        return this;
    }

    public String getUsername() {
        return $(Selectors.byCssSelector(".welcome-text span"))
                .shouldBe(visible, Duration.ofSeconds(10))
                .getText();
    }
}
