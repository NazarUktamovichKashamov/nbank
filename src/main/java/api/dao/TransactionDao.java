package api.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDao {
    private int id;
    private long senderAccountId;
    private long receiverAccountId;
    private double amount;
    private String status;
    private String createdAt;
}