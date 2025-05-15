package com.ChiragRathava.modules;

import com.ChiragRathava.pojos.*;
import com.google.gson.Gson;

public class PayloadManager {

    // Convert Java Objects to JSON
    // Gson -> Ser and DeSer.
    Gson gson;

    public String createPayloadBookingAsString() {

        Booking booking = new Booking();
        booking.setFirstname("Chirag");
        booking.setLastname("Rathava");
        booking.setTotalprice(112);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        // Java Object -> JSON
        Gson gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        System.out.println(jsonStringBooking);
        return jsonStringBooking;
    }
    // Converting the string to the JAVA Object
    public BookingResponse bookingResponseJava(String responseString){
        gson = new Gson();
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
    }

    public String setAuthPayload(){
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        gson = new Gson();
        String jsonPayloadString = gson.toJson(auth);
        System.out.println("payload set to the -> " + jsonPayloadString);
        return jsonPayloadString;
    }

    //JSON to Java
    public String getTokenFromJSON(String tokenResponse){
        gson = new Gson();
        TokenRespose tokenRespose1 = gson.fromJson(tokenResponse, TokenRespose.class);
        return tokenRespose1.getToken().toString();
    }

    public Booking getResponseFromJSON(String getResponse){
        gson = new Gson();
        Booking booking = gson.fromJson(getResponse, Booking.class);
        return booking;
    }

    public String fullUpdatePayloadAsString(){
        Booking booking = new Booking();
        booking.setFirstname("Dip");
        booking.setLastname("Rathava");
        booking.setTotalprice(112);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        return gson.toJson(booking);

    }

    /* TestIntegrationFlowE2E_02 */

    // TC#INT2 - Step 3
    public String partialUpdatePayloadAsString() {
        Booking booking = new Booking();
        booking.setFirstname("UpdatedName");
        booking.setLastname("UpdatedLastName");

        gson = new Gson();
        return gson.toJson(booking);
    }

    // TC#INT2 - Priority 7
    public String createMinimalBookingPayloadAsString() {
        Booking minimalBooking = new Booking();
        minimalBooking.setFirstname("Minimal");
        minimalBooking.setLastname("Test");
        minimalBooking.setTotalprice(100);
        minimalBooking.setDepositpaid(true);

        Bookingdates dates = new Bookingdates();
        dates.setCheckin("2024-03-01");
        dates.setCheckout("2024-03-02");
        minimalBooking.setBookingdates(dates);

        gson = new Gson();
        return gson.toJson(minimalBooking);
    }

    // TC#INT2 - Priority 8
    public String createInvalidDatesBookingPayloadAsString() {
        Booking invalidDatesBooking = new Booking();
        invalidDatesBooking.setFirstname("DateTest");
        invalidDatesBooking.setLastname("User");
        invalidDatesBooking.setTotalprice(100);
        invalidDatesBooking.setDepositpaid(true);

        Bookingdates invalidDates = new Bookingdates();
        invalidDates.setCheckin("2024-03-02"); // Checkout before checkin
        invalidDates.setCheckout("2024-03-01");
        invalidDatesBooking.setBookingdates(invalidDates);

        gson = new Gson();
        return gson.toJson(invalidDatesBooking);
    }

    // TC#INT2 - Priority 9
    public String createSpecialCharactersBookingPayloadAsString() {
        Booking specialCharBooking = new Booking();
        specialCharBooking.setFirstname("John-Doe");
        specialCharBooking.setLastname("O'Connor");
        specialCharBooking.setTotalprice(150);
        specialCharBooking.setDepositpaid(true);

        Bookingdates dates = new Bookingdates();
        dates.setCheckin("2024-03-01");
        dates.setCheckout("2024-03-02");
        specialCharBooking.setBookingdates(dates);

        gson = new Gson();
        return gson.toJson(specialCharBooking);
    }

    // TC#INT2 - Priority 10
    public String createMaxPriceBookingPayloadAsString() {
        Booking maxPriceBooking = new Booking();
        maxPriceBooking.setFirstname("MaxPrice");
        maxPriceBooking.setLastname("Test");
        maxPriceBooking.setTotalprice(Integer.MAX_VALUE);
        maxPriceBooking.setDepositpaid(true);

        Bookingdates dates = new Bookingdates();
        dates.setCheckin("2024-03-01");
        dates.setCheckout("2024-03-02");
        maxPriceBooking.setBookingdates(dates);

        gson = new Gson();
        return gson.toJson(maxPriceBooking);
    }
}
