package Iteration2;

import Requests.ChangeNameRequest;
import generators.RandomData;
import models.ChangeNameRequestModel;
import models.ChangeNameResponseModel;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChangeNameTest extends BaseTest{

    @Test
    public void positiveChangeNameTest(){
        ChangeNameRequestModel changeNameRequest = ChangeNameRequestModel.builder()
                .name(RandomData.CreateValidName())
                .build();
        new ChangeNameRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.ValidUsernameRequest())
                .post(changeNameRequest);
    }


    @Test
    public void negativeUsernameOneWordTest(){
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateSingleWordName())
                .build();
        new ChangeNameRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest())
                .post(changeNameRequestModel);
    }


    @Test
    public void negativeUsername3WordsTest(){
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateThreeWordsInvalidName())
                .build();
        new ChangeNameRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest())
                .post(changeNameRequestModel);
    }


    @Test
    public void negativeUsernameWithDigitsTest(){
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateNumericName())
                .build();
        new ChangeNameRequest(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest())
                .post(changeNameRequestModel);
    }
}
