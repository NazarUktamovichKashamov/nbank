package api.models;

import api.generators.GeneratingRule;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChangeNameRequestModel extends BaseModel{
    @GeneratingRule(regex = "^[A-Za-z]{1,15} [A-Za-z]{1,15}$")
    String name;
}
