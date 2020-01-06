package com.bookinggo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * Controls operation of Rideways Rest Application
 */
@SpringBootApplication
public class RidewaysRestApp {

    /***
     * Runs Spring Boot Application
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(RidewaysRestApp.class, args);
    }
}
