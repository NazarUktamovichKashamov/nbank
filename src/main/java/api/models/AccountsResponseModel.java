package api.models;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountsResponseModel extends BaseModel{
    private long id;
    private String accountNumber;
    private double balance;
    private List<Object> transactions;
}
