package com.ChiragRathava.tests.crud;

import com.ChiragRathava.base.BaseTest;
import com.ChiragRathava.endpoints.APIConstants;
import com.ChiragRathava.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCreateBooking extends BaseTest {

    // Create A Booking, Create a Token
    // Verify that Get booking
    // Update the Booking
    // Delete the Booking

    @Test(groups = "reg", priority = 1)
    @Owner("Chirag")
    @Description("TC#INT1 - Step 1, Verify that the Booking can be Created")
    public void testCreateBookingPOST(){

        r.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

//        System.out.println("Request Payload: " + payloadManager.createPayloadBookingAsString());

        response = RestAssured.given(r)
                .when()
                .body(payloadManager.createPayloadBookingAsString())
                .post();

        vr = response.then().log().all();
        vr.statusCode(200);
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Chirag");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

    }



}
