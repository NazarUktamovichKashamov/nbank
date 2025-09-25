package ui;

import api.models.C2CRequestModel;
import common.annotations.Browsers;
import common.annotations.UserSession;
import common.extensions.BrowserMatchExtension;
import common.extensions.UserSessionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ui.pages.BankAlerts;
import ui.pages.TransferPage;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(UserSessionExtension.class)
@ExtendWith(BrowserMatchExtension.class)
public class C2CTransferUITest extends BaseUITest{


    @Test
    @UserSession
    @Browsers("chrome")
    public void UserCanSendC2CToAnotherUserTest(){
        C2CRequestModel transfer = C2CRequestModel.generateC2CWithValidNameTest();
        double initialBalance = findBalance(transfer.getSenderAccountId());

        new TransferPage().transfer(transfer.getReceiverName(), "ACC"+transfer.getReceiverAccountId(), transfer.getAmount())
                .checkAlertMessageAndAccept((BankAlerts.compileMessage(BankAlerts.C2C_TRANSFER_SUCCESS.getMessage(), transfer.getAmount())));

        double afterBalance = findBalance(transfer.getSenderAccountId());

        assertEquals(transfer.getAmount(), initialBalance - afterBalance);
        //assertTrue(isC2CExists(transfer.getSenderAccountId(), transfer.getAmount()));



    }

    @Test
    @UserSession
    @Browsers("chrome")
    public void UserCantSendMe2MeWithInvalidNameTest(){
        C2CRequestModel transfer = C2CRequestModel.generateMe2MeWithInvalidNameTest();

        double initialBalance = findBalance(transfer.getSenderAccountId());

        new TransferPage().transfer(transfer.getReceiverName(), "ACC"+transfer.getReceiverAccountId(), transfer.getAmount())
                .checkAlertMessageAndAccept((BankAlerts.C2C_TRANSFER_ERROR_WRONG_NAME.getMessage()));

        double afterBalance = findBalance(transfer.getSenderAccountId());

        assertEquals(initialBalance, afterBalance);
        //assertFalse(isC2CExists(transfer.getSenderAccountId(), transfer.getAmount()));
    }
}