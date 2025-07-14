package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIBase{
    private static final Logger logger = LogManager.getLogger(APIBase.class);
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

    /**
     * Validates response and logs detailed information for successful and failed requests
     * @param response The HTTP response to validate
     * @param operation The name of the operation being performed (e.g., "Create Board")
     * @return true if response is successful (2xx), false otherwise
     */
    protected boolean validateResponse(Response response, String operation) {
        int statusCode = response.getStatusCode();
        long responseTime = response.getTime();
        
        // Log response details
        logger.debug("📊 {} Response - Status: {}, Time: {}ms", operation, statusCode, responseTime);
        
        if (statusCode >= 200 && statusCode < 300) {
            logger.info("✅ {} successful - Status: {}", operation, statusCode);
            return true;
        } else {
            logErrorResponse(response, operation);
            return false;
        }
    }

    /**
     * Logs detailed error information based on HTTP status code
     * @param response The failed HTTP response
     * @param operation The name of the operation that failed
     */
    private void logErrorResponse(Response response, String operation) {
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        long responseTime = response.getTime();
        
        // Log basic error information
        logger.error("❌ {} failed with status code: {}", operation, statusCode);
        logger.error("📄 Error response body: {}", responseBody);
        logger.error("⏱️ Response time: {}ms", responseTime);
        
        // Log specific error messages based on status code
        logStatusCodeSpecificMessage(statusCode, operation);
    }

    /**
     * Logs specific error messages based on HTTP status codes
     * @param statusCode The HTTP status code
     * @param operation The operation name for context
     */
    private void logStatusCodeSpecificMessage(int statusCode, String operation) {
        switch (statusCode) {
            case 400:
                logger.error("🔍 Bad Request: Check request parameters and data format for {}", operation);
                break;
            case 401:
                logger.error("🔐 Unauthorized: Check your API key and token configuration");
                logger.error("💡 Tip: Verify credentials in config.properties file");
                break;
            case 403:
                logger.error("🚫 Forbidden: You don't have permission to perform this operation");
                logger.error("💡 Tip: Check if your Trello account has the required permissions");
                break;
            case 404:
                logger.error("🔍 Not Found: The requested resource does not exist");
                logger.error("💡 Tip: Verify the resource ID is correct and the resource hasn't been deleted");
                break;
            case 429:
                logger.error("⏳ Rate Limited: Too many requests sent to Trello API");
                logger.error("💡 Tip: Wait before retrying, consider implementing retry logic with backoff");
                break;
            case 500:
                logger.error("🛠️ Internal Server Error: Trello server encountered an error");
                logger.error("💡 Tip: This is a server-side issue, try again later");
                break;
            case 502:
                logger.error("🔗 Bad Gateway: Problem with Trello's server infrastructure");
                break;
            case 503:
                logger.error("🚧 Service Unavailable: Trello service is temporarily unavailable");
                break;
            case 504:
                logger.error("⏰ Gateway Timeout: Request timeout occurred");
                break;
            default:
                logger.error("❓ Unexpected HTTP status code: {}", statusCode);
                logger.error("💡 Tip: Check Trello API documentation for this status code");
        }
    }

    /**
     * Validates input parameters before making API calls
     * @param value The value to validate
     * @param paramName The parameter name for error messages
     * @param operation The operation name for context
     * @return true if valid, false otherwise
     */
    protected boolean validateInput(String value, String paramName, String operation) {
        if (value == null || value.trim().isEmpty()) {
            logger.error("❌ {} failed: {} cannot be null or empty", operation, paramName);
            return false;
        }
        return true;
    }

    /**
     * Validates that an object is not null
     * @param object The object to validate
     * @param objectName The object name for error messages
     * @param operation The operation name for context
     * @return true if not null, false otherwise
     */
    protected boolean validateNotNull(Object object, String objectName, String operation) {
        if (object == null) {
            logger.error("❌ {} failed: {} cannot be null", operation, objectName);
            return false;
        }
        return true;
    }

}
