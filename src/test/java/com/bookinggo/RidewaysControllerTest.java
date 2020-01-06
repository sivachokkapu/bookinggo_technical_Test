package com.bookinggo;

import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static com.bookinggo.RidewaysController.callTaxiAPI;
import static com.bookinggo.RidewaysController.parse;

class RidewaysControllerTest {

    @After
    public void TearDown(){
        RidewaysApp.carList.clear();
    }

    /***
     * checks for correct outcome when valid data is used in taxi api call
     * should result in status:200
     */
    @Test
    public void callTaxiApiTest200(){
        // Arrange
        int status;
        String testPickup = "51.470020,-0.454295";
        String testDropoff = "51.470020,-0.454295";
        // Act
        String url = "https://techtest.rideways.com/dave" + "?pickup=" + testPickup + "&dropoff=" + testDropoff;
        status = callTaxiAPI("Dave's Taxis", url);
        // Assert
        Assert.assertEquals(status, 200);
    }

    /***
     * checks for correct outcome when invalid pickup and dropoff data is used in api call
     * should result in status:400
     */
    @Test
    public void callTaxiApiTest400(){
        // Arrange
        int status;
        String testPickup = "invalid pickup";
        String testDropoff = "invalid dropoff";
        // Act
        String url = "https://techtest.rideways.com/dave" + "?pickup=" + testPickup + "&dropoff=" + testDropoff;
        status = callTaxiAPI("Dave's Taxis", url);
        // Assert
        Assert.assertEquals(status, 400);
    }

    /***
     * checks for correct outcome when extreme pickup and dropoff data is used in api call
     * should result in status:500
     */
    @Test
    public void callTaxiApiTest500(){
        // Arrange
        int status;
        String testPickup = "1000";
        String testDropoff = "1000";
        // Act
        String url = "https://techtest.rideways.com/dave" + "?pickup=" + testPickup + "&dropoff=" + testDropoff;
        status = callTaxiAPI("Dave's Taxis", url);
        // Assert
        Assert.assertEquals(status, 500);
    }

    /***
     * checks for correct outcome when valid data is parsed
     * should result in carList being populated
     */
    @Test
    public void parseTestCarsFound(){
        // Arrange
        String responseBody = "{\"supplier_id\":\"ERIC\",\"pickup\":\"51.470020,-0.454295\",\"dropoff\":\"51.00000,1.0000\",\"options\":[{\"car_type\":\"EXECUTIVE\",\"price\":337878},{\"car_type\":\"PEOPLE_CARRIER\",\"price\":697395},{\"car_type\":\"LUXURY_PEOPLE_CARRIER\",\"price\":980743}]}";
        String supplier = "Eric";
        // Act
        parse(responseBody,supplier);
        // Assert
        Assert.assertFalse(RidewaysApp.carList.isEmpty());
    }

}