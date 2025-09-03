package ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ChangeNamePage extends BasePage<ChangeNamePage>{
    @Override
    public String url() {
        return "/edit-profile";
    }

    private SelenideElement nameSpace = $(Selectors.byAttribute("placeholder", "Enter new name"));
    private SelenideElement confirmButton = $(Selectors.byAttribute("class", "btn btn-primary mt-3"));


    public ChangeNamePage changeName(String newName){
        nameSpace.clear();
        nameSpace.sendKeys(newName);
        confirmButton.click();
        return this;
    }



}
