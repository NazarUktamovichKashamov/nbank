package ui;

import api.configs.Config;
import com.codeborne.selenide.Selenide;
import api.models.C2CRequestModel;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import ui.pages.BankAlerts;
import ui.pages.TransferPage;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class C2CTransferUITest extends BaseUITest{

    @Test
    public void UserCanSendC2CToAnotherUserTest(){

        C2CRequestModel transfer = C2CRequestModel.builder().senderAccountId(1).receiverAccountId(2).amount(2).recieverName("Unneccesary").build();
        double initialBalance = findBalance(transfer.getSenderAccountId());
        authAsUser(Config.getProperty("user.login"), Config.getProperty("user.password"));

        Selenide.open("/transfer");

        new TransferPage().open().transfer(transfer.getRecieverName(), "ACC"+transfer.getReceiverAccountId(), transfer.getAmount())
                .checkAlertMessageAndAccept((BankAlerts.C2C_TRANSFER_SUCCESS_FIRST_PART.getMessage()+"$"+transfer.getAmount()+BankAlerts.C2C_TRANSFER_SUCCESS_SECOND_PART.getMessage()));

        double afterBalance = findBalance(transfer.getSenderAccountId());

        assertEquals(transfer.getAmount(), initialBalance - afterBalance);


    }

    @Test
    public void UserCantSendMe2MeWithInvalidNameTest(){
        C2CRequestModel transfer = C2CRequestModel.builder().senderAccountId(1).receiverAccountId(1).amount(-2).recieverName("Unnec ces ary").build();

        authAsUser(Config.getProperty("user.login"), Config.getProperty("user.password"));

        double initialBalance = findBalance(transfer.getSenderAccountId());


        new TransferPage().open().transfer(transfer.getRecieverName(), "ACC"+transfer.getReceiverAccountId(), transfer.getAmount())
                .checkAlertMessageAndAccept((BankAlerts.C2C_TRANSFER_ERROR_NEGATIVE_AMOUNT.getMessage()));

        double afterBalance = findBalance(transfer.getSenderAccountId());

        assertEquals(initialBalance, afterBalance);
    }
}
