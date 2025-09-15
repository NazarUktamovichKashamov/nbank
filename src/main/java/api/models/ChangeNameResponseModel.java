package api.models;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChangeNameResponseModel extends BaseModel{
    private String message;
    private Customer customer;
}
