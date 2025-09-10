package ui;

import com.codeborne.selenide.Selenide;
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

        C2CRequestModel transfer = C2CRequestModel.builder().senderAccountId(1).receiverAccountId(2).amount(2).recieverName("Unneccesary").build();
        double initialBalance = findBalance(transfer.getSenderAccountId());

        Selenide.open("/transfer");

        new TransferPage().open().transfer(transfer.getRecieverName(), "ACC"+transfer.getReceiverAccountId(), transfer.getAmount())
                .checkAlertMessageAndAccept((BankAlerts.compileMessage(BankAlerts.C2C_TRANSFER_SUCCESS.getMessage(), transfer.getAmount())));

        double afterBalance = findBalance(transfer.getSenderAccountId());

        assertEquals(transfer.getAmount(), initialBalance - afterBalance);


    }

    @Test
    @UserSession
    @Browsers("chrome")
    public void UserCantSendMe2MeWithInvalidNameTest(){
        C2CRequestModel transfer = C2CRequestModel.builder().senderAccountId(1).receiverAccountId(1).amount(-2).recieverName("Unnec ces ary").build();


        double initialBalance = findBalance(transfer.getSenderAccountId());


        new TransferPage().open().transfer(transfer.getRecieverName(), "ACC"+transfer.getReceiverAccountId(), transfer.getAmount())
                .checkAlertMessageAndAccept((BankAlerts.C2C_TRANSFER_ERROR_WRONG_NAME.getMessage()));

        double afterBalance = findBalance(transfer.getSenderAccountId());

        assertEquals(initialBalance, afterBalance);
    }
}
