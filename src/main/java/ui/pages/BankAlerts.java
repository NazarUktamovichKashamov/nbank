package ui.pages;

import lombok.Getter;

@Getter
public enum BankAlerts {
C2C_TRANSFER_SUCCESS_FIRST_PART("✅ Successfully transferred "),

C2C_TRANSFER_SUCCESS_SECOND_PART(" to account ACC2!"),

C2C_TRANSFER_ERROR_NEGATIVE_AMOUNT("❌ Error: Invalid transfer: insufficient funds or invalid accounts"),

DEPOSIT_INVALID_AMOUNT("❌ Please enter a valid amount."),

DEPOSIT_SUCCESS_FIRST_PART("✅ Successfully deposited $"),

DEPOSIT_SUCCESS_SECOND_PART(" to account ACC1!"),

    CHANGE_NAME_SUCCESS("✅ Name updated successfully!"),

    CHANGE_NAME_ERROR("Name must contain two words with letters only");

    private final String message;

    BankAlerts(String message) {
        this.message = message;
    }
}
