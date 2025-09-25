package api.models;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChangeNameResponseModel extends BaseModel {
    private String message;
    private Customer customer;

    @Data
    public static class Customer {
        private int id;
        private String username;
        private String password;
        private String name;
        private String role;
        private List<Account> accounts;
    }
}

