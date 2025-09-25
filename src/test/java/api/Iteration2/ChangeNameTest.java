package api.Iteration2;

import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.requests.steps.DataBaseSteps;
import api.dao.UserDao;
import api.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomData;
import api.generators.RandomModelGenerator;
import api.models.ChangeNameRequestModel;
import api.models.ChangeNameResponseModel;
import org.junit.jupiter.api.Test;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ChangeNameTest extends BaseTest {

    @Test
    public void positiveChangeNameTest() {

        ChangeNameRequestModel changeNameRequestModel = RandomModelGenerator.generate(ChangeNameRequestModel.class);

        ChangeNameResponseModel changeNameResponseModel = new ValidatedCrudRequester<ChangeNameResponseModel>(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.ValidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .update(changeNameRequestModel);
        softly.assertThat(changeNameRequestModel.getName()).isEqualTo(changeNameResponseModel.getCustomer().getName());

        UserDao userDao = DataBaseSteps.getUserByUsername("Kate1998");
        assertEquals(changeNameResponseModel.getCustomer().getName(), userDao.getName());

    }


    @Test
    public void negativeUsernameOneWordTest() {
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateSingleWordName())
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .update(changeNameRequestModel);

        assertNull(DataBaseSteps.getUserByUsername(changeNameRequestModel.getName()));
    }


    @Test
    public void negativeUsername3WordsTest() {
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateThreeWordsInvalidName())
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .update(changeNameRequestModel);

        assertNull(DataBaseSteps.getUserByUsername(changeNameRequestModel.getName()));
    }


    @Test
    public void negativeUsernameWithDigitsTest() {
        ChangeNameRequestModel changeNameRequestModel = ChangeNameRequestModel.builder()
                .name(RandomData.CreateNumericName())
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.InvalidUsernameRequest(),
                Endpoint.CHANGE_NAME_ENDPOINT)
                .update(changeNameRequestModel);

        assertNull(DataBaseSteps.getUserByUsername(changeNameRequestModel.getName()));
    }
}
