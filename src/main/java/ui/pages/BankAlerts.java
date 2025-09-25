package ui.pages;

import lombok.Getter;

@Getter
public enum BankAlerts {
C2C_TRANSFER_SUCCESS("✅ Successfully transferred $%s to account ACC2!"),

C2C_TRANSFER_ERROR_WRONG_NAME("❌ The recipient name does not match the registered name."),

DEPOSIT_INVALID_AMOUNT("❌ Please enter a valid amount."),

DEPOSIT_SUCCESS("✅ Successfully deposited $%s to account ACC1!"),

    CHANGE_NAME_SUCCESS("✅ Name updated successfully!"),

    CHANGE_NAME_ERROR("Name must contain two words with letters only");

    private final String message;

    BankAlerts(String message) {
        this.message = message;
    }

    public static String compileMessage(String message, int amount) {
        return String.format(message, amount);
    }
    public static String compileMessage(String message, long amount) {
        return String.format(message, amount);
    }
    public static String compileMessage(String message, double amount) {
        return String.format(message, amount);
    }
}
