package ui;

import api.configs.Config;
import com.codeborne.selenide.Condition;
import api.generators.RandomData;
import api.models.ChangeNameRequestModel;
import org.junit.jupiter.api.Test;
import ui.pages.BankAlerts;
import ui.pages.ChangeNamePage;
import ui.pages.DashboardPage;


public class ChangeNameUITest extends BaseUITest{

    @Test
    public void userCanChangeNameWithValidData(){
        ChangeNameRequestModel user = ChangeNameRequestModel.builder().name(RandomData.CreateValidName()).build();
        authAsUser(Config.getProperty("user.login"), Config.getProperty("user.password"));

        new ChangeNamePage().open().changeName(user.getName())
                .checkAlertMessageAndAccept((BankAlerts.CHANGE_NAME_SUCCESS.getMessage()));

        new DashboardPage().open().getWelcomeText().shouldHave(Condition.text(user.getName()));
    }

    @Test
    public void userCanChangeNameWithInvalidData(){
        ChangeNameRequestModel user = ChangeNameRequestModel.builder().name(RandomData.CreateSingleWordName()).build();
        authAsUser(Config.getProperty("user.login"), Config.getProperty("user.password"));

        new ChangeNamePage().open().changeName(user.getName())
                .checkAlertMessageAndAccept((BankAlerts.CHANGE_NAME_ERROR.getMessage()));

        new DashboardPage().open().getWelcomeText().shouldNotHave(Condition.text(user.getName()));
    }
}
