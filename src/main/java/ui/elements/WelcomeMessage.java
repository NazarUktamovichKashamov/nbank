package ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;

@Getter
public class WelcomeMessage extends BaseElement {
    private final String username;

    public WelcomeMessage(SelenideElement element) {
        super(element);
        element.shouldBe(visible);
        this.username = find(By.cssSelector("span")).getText();
    }
}