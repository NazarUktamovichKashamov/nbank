package ui;

import api.Iteration2.BaseTest;
import api.configs.Config;
import api.specs.RequestSpecs;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BaseUITest extends BaseTest {

    @BeforeAll
    public static void setupSelenoid(){
        Configuration.baseUrl = Config.getProperty("uiBaseUrl");
        Configuration.browserSize = Config.getProperty("browserSize");
        Configuration.browser = Config.getProperty("browser");
        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true, "enableLog", true)
        );
    }
}
