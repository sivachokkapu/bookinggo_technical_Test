package com.bookinggo;


import java.util.*;

import static com.bookinggo.RidewaysController.callTaxiAPI;

/***
 *  Controls the operation of the Rideways app
 */
public class RidewaysApp {

    // initialise variables and data structures
    public static List<Car> carList = new ArrayList<>();
    public static HashMap<String, Car> cheapestCars = new HashMap<>();
    private static Car car;
    private static String url;
    private static int option;
    private static int noOfPassengers;
    private static int price;


    /***
     * Takes input arguments and selects the desired output option
     * @param args [0] - output option {1,2,3}, [1] pickup {lat,long}, [2] dropoff {lat,long}, [3] number of passengers {n}
     */
    public static void main(String[] args) {
        // checks if no arguments are input
        if (args.length == 0) {
            System.out.println("No input arguments");
            return;
        }

        // takes input option
        option = Integer.valueOf(args[0]);
        // checks if there is a 4th argument to be taken as the noOfPassengers
        if (args.length > 3) {
            noOfPassengers = Integer.valueOf(args[3]);
        }

        // switch to select output method
        switch (option) {
            case 1:
                searchDavesTaxisCars(args);
                break;
            case 2:
                searchDaveTaxisCarsWithNoOfPassengers(args, noOfPassengers);
                break;
            case 3:
                searchCheapestCars(args, noOfPassengers);
                break;
            default:
                System.out.println("Invalid option selected");
        }
    }

    /***
     * produces output for searching dave's taxis without taking noOfPassengers argument and produces output in format {car type} - {price}
     * @param args [1] pickup {lat,long}, [2] dropoff {lat,long}
     */
    public static void searchDavesTaxisCars(String[] args) {
        // checks if valid amount of arguments
        if (checkArgsLength(args.length)) return;

        // builds url and calls api for dave's taxis
        url = "https://techtest.rideways.com/dave" + "?pickup=" + args[1] + "&dropoff=" + args [2];
        callTaxiAPI("Dave's Taxis", url);

        // produces output from carList
        for (Car car:carList) {
            System.out.println(car.getCarType() + " - " + car.getPrice());
        }
    }

    /***
     * produces output for searching dave's taxis taking noOfPassengers into consideration and produces output in format {car type} - {price}
     * @param args [1] pickup {lat,long}, [2] dropoff {lat,long}
     * @param noOfPassengers taken from args[3] in main()
     */
    public static void searchDaveTaxisCarsWithNoOfPassengers(String[] args, int noOfPassengers) {
        // check if valid amount of arguments
        if (checkArgsLength(args.length)) return;

        // builds url and calls api for dave's taxis
        url = "https://techtest.rideways.com/dave" + "?pickup=" + args[1] + "&dropoff=" + args [2];
        callTaxiAPI("Dave's Taxis", url);

        // produces output from carList
        for (Car car:carList) {
            // only outputs cars which have enough seats for passengers
            if (noOfPassengers <= car.getPassengers()) {
                System.out.println(car.getCarType() + " - " + car.getPrice());
            }
        }
    }

    /***
     * checks all taxi apis and then chooses cheapest suppliers and produces output in format {car type} - {supplier} - {price}
     * @param args [1] pickup {lat,long}, [2] dropoff {lat,long}
     * @param noOfPassengers taken from args[3] in main()
     */
    public static void searchCheapestCars(String[] args, int noOfPassengers) {
        // check if valid number of arguments
        if (checkArgsLength(args.length)) return;

        // builds urls and calls all taxi apis
        url = "https://techtest.rideways.com/dave" + "?pickup=" + args[1] + "&dropoff=" + args [2];
        callTaxiAPI("Dave's Taxis", url);
        url = "https://techtest.rideways.com/eric" + "?pickup=" + args[1] + "&dropoff=" + args [2];
        callTaxiAPI("Eric's Taxis", url);
        url = "https://techtest.rideways.com/jeff" + "?pickup=" + args[1] + "&dropoff=" + args [2];
        callTaxiAPI("Jeff's Taxis", url);

        // selects cheapest suppliers
        chooseCheapestSuppliers();

        // allows for iteration on cheapestCars hashMap
        Iterator cheapestCarsIterator = cheapestCars.entrySet().iterator();

        // produces output from cheapestCars hashMap
        while (cheapestCarsIterator.hasNext()) {
            Map.Entry cheapestCar = (Map.Entry)cheapestCarsIterator.next();
            car = ((Car)cheapestCar.getValue());
            // only outputs cars which have enough seats for passengers
            if (noOfPassengers <= car.getPassengers()) {
                System.out.println(car.getCarType() + " - " + car.getSupplier() + " - " + car.getPrice());
            }
        }

    }

    /***
     * Checks if less than 3 arguments
     * @param argsLength number of arguments
     * @return true: if not enough arguments for pickup and dropoff, false: if 3 or more (enough args)
     */
    private static boolean checkArgsLength(int argsLength) {
        if (argsLength < 3) {
            System.out.println("Please enter pickup and drop off parameters");
            return true;
        }

        return false;
    }


    /***
     * Loops through carList and updates cheapestCars hashMap with cheapest cars
     */
    public static void chooseCheapestSuppliers() {
        // loop through carList
        for (Car car:carList) {
            // checks if car type is present in hashMap
            if (cheapestCars.containsKey(car.getCarType())) {
                // gets price of the existing car in hashMap
                price = cheapestCars.get(car.getCarType()).getPrice();
                // checks if current car is cheaper than car in hashMap
                if (car.getPrice() < price) {
                    //replaces car in hashMap with cheapest car
                    cheapestCars.replace(car.getCarType(),car);
                }
            } else {
                // if car type is not present in hashMap the car will be added to the hashMap
                cheapestCars.put(car.getCarType(), car);
            }
        }

    }

}
