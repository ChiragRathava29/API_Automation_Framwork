package com.ChiragRathava.tests.integration;

import com.ChiragRathava.base.BaseTest;
import com.ChiragRathava.endpoints.APIConstants;
import com.ChiragRathava.pojos.Booking;
import com.ChiragRathava.pojos.BookingResponse;
import com.ChiragRathava.pojos.Bookingdates;
import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlowE2E_02 extends BaseTest {

    private Gson gson = new Gson();

    // Test Integration Scenario 2
    // 1. Create a Booking with specific data
    // 2. Verify the booking details
    // 3. Update specific fields using PATCH
    // 4. Verify the partial update
    // 5. Delete the booking

    @Test(groups = "qa", priority = 1)
    @Owner("Chirag")
    @Description("TC#INT2 - Step 1, Create a new booking with specific test data")
    public void testCreateBookingWithSpecificData(ITestContext iTestContext) {
        r.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(r)
                .when()
                .body(payloadManager.createPayloadBookingAsString())
                .post();

        vr = response.then().log().all();
        vr.statusCode(200);
        
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Chirag");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Chirag")
    @Description("TC#INT2 - Step 2, Verify the created booking details")
    public void testVerifyCreatedBooking(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        r.basePath(basePathGET);
        response = RestAssured.given(r)
                .when()
                .get();

        vr = response.then().log().all();
        vr.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertThat(booking.getFirstname()).isEqualTo("Chirag");
        assertThat(booking.getTotalprice()).isNotNull();
    }

    @Test(groups = "qa", priority = 3)
    @Owner("Chirag")
    @Description("TC#INT2 - Step 3, Partially update the booking")
    public void testPartialUpdateBooking(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token", token);

        String basePathPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        r.basePath(basePathPATCH);

        response = RestAssured
                .given(r)
                .cookie("token", token)
                .when()
                .body(payloadManager.partialUpdatePayloadAsString())
                .patch();

        vr = response.then().log().all();
        vr.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertThat(booking.getFirstname()).isEqualTo("UpdatedName");
    }

    @Test(groups = "qa", priority = 4)
    @Owner("Chirag")
    @Description("TC#INT2 - Step 4, Verify the partial update")
    public void testVerifyPartialUpdate(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        r.basePath(basePathGET);
        response = RestAssured.given(r)
                .when()
                .get();

        vr = response.then().log().all();
        vr.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertThat(booking.getFirstname()).isEqualTo("UpdatedName");
    }

    @Test(groups = "qa", priority = 5)
    @Owner("Chirag")
    @Description("TC#INT2 - Step 5, Delete the booking")
    public void testDeleteBooking(ITestContext iTestContext) {
        String token = (String) iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        r.basePath(basePathDELETE).cookie("token", token);
        
        vr = RestAssured.given().spec(r)
                .when()
                .delete()
                .then()
                .log().all();
        vr.statusCode(201);
    }

    // Additional Test Cases by AI

    @Test(groups = "qa", priority = 6)
    @Owner("Chirag")
    @Description("TC#INT2 - Additional Test 1: Verify booking with invalid ID returns 404")
    public void testGetBookingWithInvalidId() {
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/999999";
        r.basePath(basePathGET);

        response = RestAssured.given(r)
                .when()
                .get();

        vr = response.then().log().all();
        vr.statusCode(404);
    }

    @Test(groups = "qa", priority = 7)
    @Owner("Chirag")
    @Description("TC#INT2 - Additional Test 2: Create booking with minimum required fields")
    public void testCreateBookingWithMinimalData(ITestContext iTestContext) {
        r.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(r)
                .when()
                .body(payloadManager.createMinimalBookingPayloadAsString())
                .post();

        vr = response.then().log().all();
        vr.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertThat(bookingResponse.getBookingid()).isNotNull();
        assertThat(bookingResponse.getBooking().getFirstname()).isEqualTo("Minimal");
    }

    @Test(groups = "qa", priority = 8)
    @Owner("Chirag")
    @Description("TC#INT2 - Additional Test 3: Verify booking with invalid date order")
    public void testBookingDatesValidation(ITestContext iTestContext) {
        r.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(r)
                .when()
                .body(payloadManager.createInvalidDatesBookingPayloadAsString())
                .post();

        vr = response.then().log().all();
        vr.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertThat(bookingResponse.getBookingid()).isNotNull();
        assertThat(bookingResponse.getBooking().getBookingdates().getCheckin()).isEqualTo("2024-03-02");
        assertThat(bookingResponse.getBooking().getBookingdates().getCheckout()).isEqualTo("2024-03-01");
        
        iTestContext.setAttribute("invalid_date_booking_id", bookingResponse.getBookingid());
    }

    @Test(groups = "qa", priority = 9)
    @Owner("Chirag")
    @Description("TC#INT2 - Additional Test 4: Verify booking with special characters in name")
    public void testBookingWithSpecialCharacters(ITestContext iTestContext) {
        r.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(r)
                .when()
                .body(payloadManager.createSpecialCharactersBookingPayloadAsString())
                .post();

        vr = response.then().log().all();
        vr.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertThat(bookingResponse.getBooking().getFirstname()).isEqualTo("John-Doe");
        assertThat(bookingResponse.getBooking().getLastname()).isEqualTo("O'Connor");
    }

    @Test(groups = "qa", priority = 10)
    @Owner("Chirag")
    @Description("TC#INT2 - Additional Test 5: Verify booking with maximum price value")
    public void testBookingWithMaxPrice(ITestContext iTestContext) {
        r.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(r)
                .when()
                .body(payloadManager.createMaxPriceBookingPayloadAsString())
                .post();

        vr = response.then().log().all();
        vr.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertThat(bookingResponse.getBooking().getTotalprice()).isEqualTo(Integer.MAX_VALUE);
    }
    
}
