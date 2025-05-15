package com.ChiragRathava.tests.crud;

import com.ChiragRathava.base.BaseTest;
import com.ChiragRathava.endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestHealthCheck extends BaseTest {

    @Test(groups = "reg", priority = 1)
    @Owner("Chirag")
    @Description("TC#3 - Verify Health")
    public void testGETHealthCheck(){

        r.basePath(APIConstants.PING_URL);

//        System.out.println("Request Payload: " + payloadManager.createPayloadBookingAsString());

        response = RestAssured.given(r)
                .when()
                .get();

        vr = response.then().log().all();
        vr.statusCode(201);


    }

}
