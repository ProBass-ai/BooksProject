package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import static io.restassured.RestAssured.given;

public class Services {

    private RequestSpecification requestSpec;
    private final String baseURL;
    private String endPoint = "";
    private Map<String, String> headers;
    private String methods;
    private final String[] acceptableMethods = new String[]{"GET", "POST", "DELETE", "PUT"};


    public Services(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setEndpoint(String endpoint) {
        this.endPoint = endpoint;
    }


    public void addHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void build() {
        requestSpec = new RequestSpecBuilder().setBaseUri(baseURL).build();
    }

    private RequestSpecification request(){
        RequestSpecification requestModifier = given().spec(requestSpec);
        if(headers!=null){
            requestModifier.headers(headers);
        }


        return requestModifier;
    }

    public void setHTTPMethod(String method) {
        if(Arrays.stream(acceptableMethods).anyMatch(Predicate.isEqual(method.toUpperCase()))) {
            this.methods = method.toUpperCase();
        }
        else{
            throw new IllegalArgumentException("Invalid method: " + method
            +"\nValid methods: " + Arrays.toString(acceptableMethods));
        }
    }

    public Response send() {

        switch(methods) {
            case "POST":
                return request().when().redirects().follow(true).post(endPoint);
            case "PUT":
                return request().put(endPoint);
            case "DELETE":
                return request().delete(endPoint);
            default:
                return request().get(endPoint);
        }
    }
}
