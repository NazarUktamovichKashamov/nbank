package ui;

import api.models.DepositRequestModel;
import common.annotations.Browsers;
import common.annotations.UserSession;
import common.extensions.BrowserMatchExtension;
import common.extensions.UserSessionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import ui.pages.BankAlerts;
import ui.pages.DepositPage;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UserSessionExtension.class)
@ExtendWith(BrowserMatchExtension.class)

public class DepositUITest extends BaseUITest{

    @Test
    @Browsers("chrome")
    @UserSession
    public void userCanDeposit(){
        DepositRequestModel user = DepositRequestModel.builder().id(1).balance(100).build();

        new DepositPage().open().deposit(user.getId(), user.getBalance())
               .checkAlertMessageAndAccept((BankAlerts.compileMessage(BankAlerts.DEPOSIT_SUCCESS.getMessage(), user.getBalance())));

        assertTrue(isDepositExists(user.getId(), user.getBalance()));
    }

    @Test
    @Browsers("chrome")
    @UserSession
    public void userCanNotDepositNegativeNumber(){
        DepositRequestModel user = DepositRequestModel.builder().id(1).balance(-10).build();

        new DepositPage().open().deposit(user.getId(), user.getBalance())
                .checkAlertMessageAndAccept((BankAlerts.DEPOSIT_INVALID_AMOUNT.getMessage()));

        assertFalse(isDepositExists(user.getId(), user.getBalance()));
    }
}
