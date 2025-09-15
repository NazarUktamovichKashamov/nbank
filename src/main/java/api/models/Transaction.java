package api.models;

import lombok.Data;

@Data
public class Transaction {
    private int id;
    private double amount;
    private String type;
    private String timestamp;
    private int relatedAccountId;
}
