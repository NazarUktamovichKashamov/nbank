package api.models;

import lombok.Data;
import java.util.List;

@Data
public class Account {
    private int id;
    private String accountNumber;
    private double balance;
    private List<Transaction> transactions;
}
