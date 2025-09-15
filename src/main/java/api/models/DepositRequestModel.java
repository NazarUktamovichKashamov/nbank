package api.models;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DepositRequestModel extends BaseModel {
    private int id;
    private long balance;
}
