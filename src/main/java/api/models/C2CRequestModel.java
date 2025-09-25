package api.models;

import api.generators.RandomData;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class C2CRequestModel extends BaseModel {
    private String receiverName;
    private int senderAccountId;
    private int receiverAccountId;
    private int amount;

    public static C2CRequestModel generateMe2MeWithInvalidNameTest(){
        return C2CRequestModel.builder()
                .senderAccountId(7)
                .receiverAccountId(2)
                .amount(Integer.parseInt(RandomData.CreateNumber()))
                .receiverName(RandomData.CreateThreeWordsInvalidName())
                .build();
    }

    public static C2CRequestModel generateMe2MeWithValidNameTest(){
        return C2CRequestModel.builder()
                .senderAccountId(7)
                .receiverAccountId(7)
                .amount(Integer.parseInt(RandomData.CreateNumber()))
                .receiverName(RandomData.CreateThreeWordsInvalidName())
                .build();
    }

    public static C2CRequestModel generateC2CWithValidNameTest(){
        return C2CRequestModel.builder()
                .receiverName(RandomData.CreateValidName())
                .amount(Integer.parseInt(RandomData.CreateNumber()))
                .senderAccountId(7)
                .receiverAccountId(2)
                .build();
    }
}
