package Requests.skeleton.requesters;

import Requests.skeleton.Endpoint;
import Requests.skeleton.HttpRequest;
import Requests.skeleton.interfaces.CrudEndpointInterface;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.BaseModel;

public class ValidatedCrudRequester<M extends BaseModel> extends HttpRequest implements CrudEndpointInterface {
    private CrudRequester crudRequester;
    public ValidatedCrudRequester(RequestSpecification requestSpecification, ResponseSpecification responseSpecification, Endpoint endpoint) {
        super(requestSpecification, responseSpecification, endpoint);
        this.crudRequester = new CrudRequester(requestSpecification, responseSpecification, endpoint);
    }

    @Override
    public M post(BaseModel model) {
        return (M) crudRequester.post(model).extract().as(endpoint.getResponseModel());
    }

    @Override
    public Object get(long id) {
        return null;
    }

    @Override
    public Object update(long id, BaseModel model) {
        return null;
    }

    @Override
    public Object update(long id) {
        return null;
    }
}
