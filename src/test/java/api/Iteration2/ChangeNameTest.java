package api.Iteration2;

import api.Requests.skeleton.Endpoint;
import api.Requests.skeleton.requesters.CrudRequester;
import api.Requests.skeleton.requesters.ValidatedCrudRequester;
import api.generators.RandomData;
import api.generators.RandomModelGenerator;
import api.models.ChangeNameRequestModel;
import api.models.ChangeNameResponseModel;
import org.junit.jupiter.api.Test;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

public class ChangeNameTest extends BaseTest{

    @Test
    public void positiveChangeNameTest(){

        ChangeNameRequestModel changeNameRequestModel = RandomModelGenerator.generate(ChangeNameRequestModel.class);

        ChangeNameResponseModel changeNameResponseModel = new ValidatedCrudRequester<ChangeNameResponseModel>(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.ValidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .update(changeNameRequestModel);
        softly.assertThat(changeNameRequestModel.getName()).isEqualTo(changeNameResponseModel.getCustomer().getName());
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
                .update(changeNameRequestModel);
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
                .update(changeNameRequestModel);
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
                .update(changeNameRequestModel);
    }
}
