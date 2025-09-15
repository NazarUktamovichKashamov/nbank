package api.Requests.skeleton;

import api.models.BaseModel;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public abstract class HttpRequest {
    protected RequestSpecification requestSpecification;
    protected ResponseSpecification responseSpecification;
    protected Endpoint endpoint;

    public HttpRequest(RequestSpecification requestSpecification, ResponseSpecification responseSpecification, Endpoint endpoint) {
        this.requestSpecification = requestSpecification;
        this.responseSpecification = responseSpecification;
        this.endpoint = endpoint;
    }

    public abstract Object update(long id, BaseModel model);
}
