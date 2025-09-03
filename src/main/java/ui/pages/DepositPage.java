package ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DepositPage extends BasePage<DepositPage>{

    private SelenideElement chooseAccount = $(Selectors.byAttribute("class", "form-control account-selector"));
    private SelenideElement enterAmount = $(Selectors.byAttribute("placeholder", "Enter amount"));
    private SelenideElement depositButton = $(Selectors.byText("\uD83D\uDCB5 Deposit"));

    @Override
    public String url() {
        return "/deposit";
    }

    public DepositPage deposit(int accountOption, long balance){
        chooseAccount.selectOption(accountOption);
        enterAmount.sendKeys(String.valueOf(balance));
        depositButton.click();
        return this;
    }

    //$("#root > div > div > button:nth-child(1)").click();
    //$(Selectors.byAttribute("class", "form-control account-selector")).selectOption(1);
    //$(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys(String.valueOf(user.getBalance()));
    //$("#root > div > button").click();
}
