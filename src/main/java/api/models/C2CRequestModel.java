package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class C2CRequestModel extends BaseModel {
    private String recieverName;
    private long senderAccountId;
    private long receiverAccountId;
    private int amount;
}
