package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIBase{
    protected static final String BASE_URL = ConfigUtil.get("baseUrl");
    protected static final String KEY = ConfigUtil.get("apiKey");
    protected static final String TOKEN = ConfigUtil.get("apiToken");

    protected RequestSpecification requestSpec;
    private Response response;

    public APIBase() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addQueryParam("key", KEY)
                .addQueryParam("token", TOKEN)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
    }

    // Get request
    protected RequestSpecification getRequest() {
        return given().spec(requestSpec);
    }


    // Request with query parameters
    protected RequestSpecification getRequestWithBody(Object body) {
        return given().spec(requestSpec).body(body);
    }

    // Expanded request with custom headers and parameters
    protected RequestSpecification getCustomRequest(Map<String, String> extraHeaders,
                                                    Map<String, Object> extraParams,
                                                    Object body) {
        RequestSpecification req = given().spec(requestSpec);

        if (extraHeaders != null) {
            for (Map.Entry<String, String> h : extraHeaders.entrySet()) {
                req.header(h.getKey(), h.getValue());
            }
        }

        if (extraParams != null) {
            for (Map.Entry<String, Object> p : extraParams.entrySet()) {
                req.queryParam(p.getKey(), p.getValue());
            }
        }

        if (body != null) {
            req.body(body);
        }

        return req;
    }

    //Get- Post-Put-Delete
    public Response sendRequest(String endpoint, String method, RequestSpecification requestSpec){
        switch (method.toUpperCase()){
            case "GET":
                response =  requestSpec.when().get(endpoint);
                break;
            case "POST":
                response = requestSpec.when().post(endpoint);
                break;
            case "PUT":
                response =  requestSpec.when().put(endpoint);
                break;
            case "DELETE":
                response =  requestSpec.when().delete(endpoint);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP Method: " + method);
        }
        setResponse(response);
        return response;

    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
