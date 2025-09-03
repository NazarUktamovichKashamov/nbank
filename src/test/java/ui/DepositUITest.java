package ui;

import api.configs.Config;
import api.models.DepositRequestModel;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import ui.pages.BankAlerts;
import ui.pages.DepositPage;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class DepositUITest extends BaseUITest{

    @Test
    public void userCanDeposit(){
        DepositRequestModel user = DepositRequestModel.builder().id(1).balance(100).build();
        authAsUser(Config.getProperty("user.login"), Config.getProperty("user.password"));

        new DepositPage().open().deposit(user.getId(), user.getBalance())
                .checkAlertMessageAndAccept((BankAlerts.DEPOSIT_SUCCESS_FIRST_PART.getMessage()+user.getBalance()+BankAlerts.DEPOSIT_SUCCESS_SECOND_PART.getMessage()));

        authAsUser(Config.getProperty("user.login"), Config.getProperty("user.password"));

        assertTrue(isDepositExists(user.getId(), user.getBalance()));
    }


    @Test
    public void userCanNotDepositNegativeNumber(){
        DepositRequestModel user = DepositRequestModel.builder().id(1).balance(-10).build();

        authAsUser(Config.getProperty("user.login"), Config.getProperty("user.password"));

        new DepositPage().open().deposit(user.getId(), user.getBalance())
                .checkAlertMessageAndAccept((BankAlerts.DEPOSIT_INVALID_AMOUNT.getMessage()));

        authAsUser(Config.getProperty("user.login"), Config.getProperty("user.password"));

        assertFalse(isDepositExists(user.getId(), user.getBalance()));


    }
}
