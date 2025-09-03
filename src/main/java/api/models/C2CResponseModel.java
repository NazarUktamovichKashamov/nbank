package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class C2CResponseModel extends BaseModel {
    private double amount;
    private String message;
    private long receiverAccountId;
    private long senderAccountId;
}
