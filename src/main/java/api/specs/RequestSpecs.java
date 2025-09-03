package api.specs;

import api.Requests.skeleton.Endpoint;
import api.Requests.skeleton.requesters.CrudRequester;
import api.configs.Config;
import api.models.LoginRequestModel;
import com.codeborne.selenide.Selenide;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static io.restassured.RestAssured.config;

public class RequestSpecs {

    private static Map<String, String> authHeaders = new HashMap<>(Map.of("user", "Basic TmF6YXIyMDA0Ok5hemFyMjAwNCE="));

    private RequestSpecs(){}

    private static RequestSpecBuilder defaultRequestBuilder(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilters(List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()))
                .setBaseUri(Config.getProperty("server"));

    }

    public static RequestSpecification userOneAuthSpec(){
        return defaultRequestBuilder()
                .addHeader("Authorization", authHeaders.get("user"))
                .build();
    }

    public static String getUserAuthHeader(String username, String password){
        String userAuthHeader;
        if (!authHeaders.containsKey(username)) {
        userAuthHeader = new CrudRequester(
                RequestSpecs.userOneAuthSpec(),
                ResponseSpecs.BlankLogin(),
                Endpoint.LOGIN_ENDPOINT
                )
                .post(LoginRequestModel.builder().username(username).password(password).build())
                .extract()
                .header("Authorization");
        Selenide.open("/");
        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", userAuthHeader);
        return userAuthHeader;
        } else {
            userAuthHeader = authHeaders.get(username);
        }
        return userAuthHeader;
    }
}
