package api.Iteration2;

import Requests.skeleton.Endpoint;
import Requests.skeleton.requesters.CrudRequester;
import Requests.skeleton.requesters.ValidatedCrudRequester;
import generators.RandomData;
import generators.RandomModelGenerator;
import models.ChangeNameRequestModel;
import models.ChangeNameResponseModel;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class ChangeNameTest extends BaseTest{

    @Test
    public void positiveChangeNameTest(){

        ChangeNameRequestModel changeNameRequestModel = RandomModelGenerator.generate(ChangeNameRequestModel.class);

        ChangeNameResponseModel changeNameResponseModel = new ValidatedCrudRequester<ChangeNameResponseModel>(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.ValidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .post(changeNameRequestModel);
        softly.assertThat(changeNameRequestModel.getName()).isEqualTo(changeNameResponseModel.getNewUsername());
    }


    @Test
    public void negativeUsernameOneWordTest(){
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateSingleWordName())
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .post(changeNameRequestModel);
    }


    @Test
    public void negativeUsername3WordsTest(){
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateThreeWordsInvalidName())
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .post(changeNameRequestModel);
    }


    @Test
    public void negativeUsernameWithDigitsTest(){
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateNumericName())
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .post(changeNameRequestModel);
    }
}
