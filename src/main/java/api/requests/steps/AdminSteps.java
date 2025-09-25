package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.models.CreateUserRequestModel;
import api.models.CreateUserResponseModel;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import common.helps.StepLogger;

import java.util.List;

public class AdminSteps {
    public static CreateUserRequestModel createUser() {
        CreateUserRequestModel userRequest =
                RandomModelGenerator.generate(CreateUserRequestModel.class);

        return StepLogger.log("Admin creates user " + userRequest.getUsername(), () -> {
                    new ValidatedCrudRequester<CreateUserResponseModel>(
                            RequestSpecs.adminSpec(),
                            ResponseSpecs.entityWasCreated(),
                            Endpoint.ADMIN_USER)
                            .post(userRequest);

                    return userRequest;
                }
        );
    }
    public static List<CreateUserResponseModel> getAllUsers() {
        return StepLogger.log("Admin gets all users", () -> {
            return new ValidatedCrudRequester<CreateUserResponseModel>(
                    RequestSpecs.adminSpec(),
                    ResponseSpecs.requestReturnsOK(),
                    Endpoint.ADMIN_USER).getAll(CreateUserResponseModel[].class);
        });
    }
}