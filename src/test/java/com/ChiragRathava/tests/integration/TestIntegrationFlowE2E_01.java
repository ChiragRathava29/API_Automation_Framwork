package com.ChiragRathava.tests.integration;

import com.ChiragRathava.base.BaseTest;
import com.ChiragRathava.endpoints.APIConstants;
import com.ChiragRathava.pojos.Booking;
import com.ChiragRathava.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlowE2E_01 extends BaseTest {

    // Test Integration Scenario 1

    // 1. Create a Booking -> bookingID

    // 2. Create Token -> token

    // 3. Create that the Create Booking is working - GET Request to bookingID

    // 4. Update the booking ( bookingID, Token) - Need to get the Token, bookingID from ...

    // 5. Delete the Booking - Need to get the token, bookingID from above request

    @Test(groups = "qa", priority = 1)
    @Owner("Chirag")
    @Description("TC#INT1 - Step 1, Verify that the Booking can be Created")
    public void testCreateBooking(ITestContext iTestContext){

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

        iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());

    }

    @Test(groups = "qa", priority = 2)
    @Owner("Chirag")
    @Description("TC#INT1 - Step 2, Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext){
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        // GET Request - to verify that the firstname after creation is Rathva
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathGET);

        r.basePath(basePathGET);
        response = RestAssured.given(r)
                .when()
                .get();
        vr = response.then().log().all();
        vr.statusCode(200);
        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("Chirag");

    }

    @Test(groups = "qa", priority = 3)
    @Owner("Chirag")
    @Description("TC#INT1 - Step 3, Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext){

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token",token);

        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        r.basePath(basePathPUTPATCH);

        response = RestAssured
                .given(r).cookie("token", token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();

        vr = response.then().log().all();
        //Validatable Assertion
        vr.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());

        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("Dip");
//        assertThat(booking.getLastname()).isNotNull().isNotBlank();
        assertThat(booking.getLastname()).isEqualTo("Rathava");

    }

    @Test(groups = "qa", priority = 4)
    @Owner("Chirag")
    @Description("TC#INT1 - Step 4, Delete the Booking by ID")
    public void testDeleteBookingId(ITestContext iTestContext) {
        String token = (String) iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathDELETE);

        r.basePath(basePathDELETE).cookie("token", token);
        vr = RestAssured.given().spec(r)
                .when().delete().then().log().all();
        vr.statusCode(201);

//        r.basePath(basePathDELETE);
//        vr = RestAssured.given()
//                .spec(r)
//                .cookie("token", token)
//                .when().delete().then().log().all();
//        vr.statusCode(201);
    }

}
