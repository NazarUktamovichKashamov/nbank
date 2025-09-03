package ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage extends BasePage<TransferPage>{
    @Override
    public String url() {
        return "/transfer";
    }

    private SelenideElement senderAccountPick = $(Selectors.byText("-- Choose an account --"));
    private SelenideElement accountToTransferFrom = $(Selectors.byText("ACC1"));
    private SelenideElement recipientName = $(Selectors.byAttribute("placeholder", "Enter recipient name"));
    private SelenideElement recipientAccountNumber = $(Selectors.byAttribute("placeholder", "Enter recipient account number"));
    private SelenideElement amount = $(Selectors.byAttribute("placeholder", "Enter amount"));
    private SelenideElement confirmTick = $(Selectors.byText("Confirm details are correct"));
    private SelenideElement sendTransferButton = $(Selectors.byText("\uD83D\uDE80 Send Transfer"));


    public TransferPage transfer(String recieverName, String recipientAccountId, int transferAmount){
        senderAccountPick.click();
        accountToTransferFrom.click();
        recipientName.sendKeys(recieverName);
        recipientAccountNumber.sendKeys(recipientAccountId);
        amount.sendKeys(String.valueOf(transferAmount));
        confirmTick.click();
        sendTransferButton.click();
        return this;
    }


    //$(Selectors.byText("-- Choose an account --")).click();
    //$(Selectors.byText("ACC1")).click();
    //$(Selectors.byAttribute("placeholder", "Enter recipient name")).sendKeys("Unnecessary");
    //$(Selectors.byAttribute("placeholder", "Enter recipient account number")).sendKeys("ACC"+transfer.getReceiverAccountId());
    //$(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys(String.valueOf(transfer.getAmount()));
    //$(Selectors.byText("Confirm details are correct")).click();
    //$(Selectors.byText("\uD83D\uDE80 Send Transfer")).click();
}
