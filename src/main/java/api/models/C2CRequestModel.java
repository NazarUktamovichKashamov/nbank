package api.models;

import api.generators.RandomData;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class C2CRequestModel extends BaseModel {
    private String recieverName;
    private int senderAccountId;
    private int receiverAccountId;
    private int amount;

    public static C2CRequestModel generateMe2MeWithInvalidNameTest(){
        return C2CRequestModel.builder()
                .senderAccountId(1)
                .receiverAccountId(1)
                .amount(Integer.parseInt(RandomData.CreateNumber()))
                .recieverName(RandomData.CreateThreeWordsInvalidName())
                .build();
    }

    public static C2CRequestModel generateMe2MeWithValidNameTest(){
        return C2CRequestModel.builder()
                .senderAccountId(1)
                .receiverAccountId(1)
                .amount(Integer.parseInt(RandomData.CreateNumber()))
                .recieverName(RandomData.CreateThreeWordsInvalidName())
                .build();
    }

    public static C2CRequestModel generateC2CWithValidNameTest(){
        return C2CRequestModel.builder()
                .amount(Integer.parseInt(RandomData.CreateNumber()))
                .senderAccountId(1)
                .receiverAccountId(1)
                .build();
    }
}
