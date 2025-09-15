package api.models;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
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
