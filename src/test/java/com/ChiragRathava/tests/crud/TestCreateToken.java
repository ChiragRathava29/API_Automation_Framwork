package com.ChiragRathava.tests.crud;

import com.ChiragRathava.base.BaseTest;
import com.ChiragRathava.endpoints.APIConstants;
import com.ChiragRathava.pojos.BookingResponse;
import com.ChiragRathava.pojos.TokenRespose;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestCreateToken extends BaseTest {

    @Test(groups = "reg", priority = 1)
    @Owner("Chirag")
    @Description("TC#2 - Create Token and Verify")
    public void testTokenPOST(){

        r.basePath(APIConstants.AUTH_URL);

//        System.out.println("Request Payload: " + payloadManager.createPayloadBookingAsString());

        response = RestAssured.given(r)
                .when()
                .body(payloadManager.setAuthPayload())
                .post();

        vr = response.then().log().all();
        vr.statusCode(200);
        String  token = payloadManager.getTokenFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(token);

    }

}
