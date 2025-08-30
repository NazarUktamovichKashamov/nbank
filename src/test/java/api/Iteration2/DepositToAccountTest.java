package api.Iteration2;

import Requests.skeleton.Endpoint;
import Requests.skeleton.requesters.CrudRequester;
import Requests.skeleton.requesters.ValidatedCrudRequester;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import models.DepositRequestModel;
import models.DepositResponseModel;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DepositToAccountTest {
        @BeforeAll
        public static void setupRestAssured() {
            RestAssured.filters(
                    List.of(new RequestLoggingFilter(),
                            new ResponseLoggingFilter()));
        }


        @Test
        public void positiveDepositTest() {
            SoftAssertions softly = new SoftAssertions();
            DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                    .id(1)
                    .balance(1000)
                    .build();
            DepositResponseModel depositResponseModel = new ValidatedCrudRequester<DepositResponseModel>
                    (RequestSpecs.userOneAuthSpec(),
                            ResponseSpecs.DepositPositiveRequest(),
                            Endpoint.USER_DEPOSIT_ENDPOINT)
                    .post(depositRequestModel);
            softly.assertThat(depositRequestModel.getId()).isEqualTo(depositResponseModel.getId());
    }


    @Test
    public void DepositZeroAmountTest() {
        DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                .id(1)
                .balance(0)
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.DepositBadRequest(),
                Endpoint.USER_DEPOSIT_ENDPOINT)
                .post(depositRequestModel);
    }


    @Test
    public void DepositNegativeAmountTest() {
        DepositRequestModel depositRequestModel= DepositRequestModel.builder()
                .id(1)
                .balance(-100)
                .build();
        new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.DepositBadRequest(),
                Endpoint.USER_DEPOSIT_ENDPOINT)
                .post(depositRequestModel);
    }
}
