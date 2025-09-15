package ui;

import api.generators.RandomData;
import api.models.ChangeNameRequestModel;
import common.annotations.Browsers;
import common.annotations.UserSession;
import common.extensions.BrowserMatchExtension;
import common.extensions.UserSessionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ui.pages.BankAlerts;
import ui.pages.ChangeNamePage;
import ui.pages.DashboardPage;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UserSessionExtension.class)
@ExtendWith(BrowserMatchExtension.class)

public class ChangeNameUITest extends BaseUITest{

    @Test
    @UserSession
    @Browsers("chrome")
    public void userCanChangeNameWithValidData(){
        ChangeNameRequestModel user = ChangeNameRequestModel.builder().name(RandomData.CreateValidName()).build();

        new ChangeNamePage().open().changeName(user.getName())
                .checkAlertMessageAndAccept((BankAlerts.CHANGE_NAME_SUCCESS.getMessage()));

        assertEquals(new DashboardPage()
                .open()
                .waitForUsernameToLoad(user.getName())
                .getUsername(), (user.getName()));
    }

    @Test
    @UserSession
    @Browsers("chrome")
    public void userCanChangeNameWithInvalidData(){
        ChangeNameRequestModel user = ChangeNameRequestModel.builder().name(RandomData.CreateSingleWordName()).build();

        new ChangeNamePage().open().changeName(user.getName())
                .checkAlertMessageAndAccept((BankAlerts.CHANGE_NAME_ERROR.getMessage()));

        assertNotEquals(new DashboardPage()
                .open()
                .getUsername(), user.getName());
    }
}
