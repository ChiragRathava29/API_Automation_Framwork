package com.ChiragRathava.base;

import com.ChiragRathava.assrts.AssertActions;
import com.ChiragRathava.endpoints.APIConstants;
import com.ChiragRathava.modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    // CommonTOAll Testcase
    // // Base URL, Content Type - json - common

    public RequestSpecification r;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse vr;

    @BeforeTest
    public void setUp(){
        // Base URL, Content Type - json
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

//        r = RestAssured
//                .given().baseUri(APIConstants.BASE_URL)
//                .contentType(ContentType.JSON).log().all();

    /* Both are same */

    // 1. Using a direct Function
//        r = RestAssured.given();
//        r.baseUri(APIConstants.BASE_URL);
//        r.contentType(ContentType.JSON).log().all();

    // 2. Using a class
        r = new RequestSpecBuilder()
                .setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type","application/json")
                .build().log().all();

    }

    public String getToken(){
        r = RestAssured
                .given()
                .baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);

        // Setting the payload
        String payload = payloadManager.setAuthPayload();

        // Get the Token
        response = r.contentType(ContentType.JSON).body(payload).post();

        //String Extraction
        String token = payloadManager.getTokenFromJSON(response.asString());

        return token;
    }

}
