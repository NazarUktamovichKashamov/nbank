package ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;

@Getter

public class DashboardPage extends BasePage<DashboardPage>{

    private SelenideElement transfer = $(Selectors.byText("\uD83D\uDD04 Make a Transfer"));
    private SelenideElement deposit = $(Selectors.byText("\uD83D\uDCB0 Deposit Money"));
    private SelenideElement createAccount = $(Selectors.byText("➕ Create New Account"));
    private SelenideElement logout = $(Selectors.byText("\uD83D\uDEAA Logout"));
    private SelenideElement welcomeText = $(Selectors.byAttribute("class","welcome-text"));

    @Override
    public String url() {
        return "/dashboard";
    }
}
