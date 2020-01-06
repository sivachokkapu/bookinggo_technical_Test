package com.bookinggo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.bookinggo.RidewaysApp.chooseCheapestSuppliers;
import static com.bookinggo.RidewaysController.callTaxiAPI;

/***
 * controls data input and output from rest api to provide rest api output
 */
@RestController
@RequestMapping("/rideways")
public class RidewaysRestController {

    /***
     * takes input through rest api and then produces output of cheapest cars through rest api
     * http://localhost:8080/rideways/cheapest-cars - uses default data
     * http://localhost:8080/rideways/cheapest-cars?pickup=51.470020,-0.454295&dropoff=51.470020,-0.454295&noOfPassengers=1
     * @param pickup pickup parameter taken from rest api
     * @param dropoff dropoff parameter taken from rest api
     * @param noOfPassengers noOfPassengers parameter taken from rest api
     * @return success: json data of cheapest cars, fail: error message
     * @throws JSONException
     */
    @RequestMapping(value = "/cheapest-cars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> cheapestCarsRestAPI(@RequestParam(value = "pickup", defaultValue = "51.470020,-0.454295") String pickup,
                                                 @RequestParam(value = "dropoff", defaultValue = "51.470020,-0.454295") String dropoff,
                                                 @RequestParam(value = "noOfPassengers", defaultValue = "0") int noOfPassengers) throws JSONException {
        // clears old search data
        RidewaysApp.carList.clear();
        RidewaysApp.cheapestCars.clear();

        // initialise variables
        String url;
        int status;

        // builds urls calls apis and retrieves status
        url = "https://techtest.rideways.com/dave" + "?pickup=" + pickup + "&dropoff=" + dropoff;
        status = callTaxiAPI("Dave's Taxis", url);
        url = "https://techtest.rideways.com/eric" + "?pickup=" + pickup + "&dropoff=" + dropoff;
        status = callTaxiAPI("Eric's Taxis", url);
        url = "https://techtest.rideways.com/jeff" + "?pickup=" + pickup + "&dropoff=" + dropoff;
        status = callTaxiAPI("Jeff's Taxis", url);

        // selects cheapest suppliers
        chooseCheapestSuppliers();

        // initialises car data structures
        Car carObject;
        List<Car> carList = new ArrayList<>();
        HashMap<String, Car> cheapestCars = RidewaysApp.cheapestCars;

        // if status is erroneous or if no available cars then error messages produced
        if (status == 400){
            return new ResponseEntity("Please enter pickup and dropoff parameters correctly", HttpStatus.OK);
        } else if (status == 500) {
            return new ResponseEntity("Something went wrong", HttpStatus.OK);
        } else if (cheapestCars.isEmpty()) {
            return new ResponseEntity("No available cars", HttpStatus.OK);
        }

        // allows for iteration through hashMap
        Iterator cheapestCarsIterator = cheapestCars.entrySet().iterator();

        // iterates through cheapestCars hashMap
        while (cheapestCarsIterator.hasNext()) {
            Map.Entry cheapestCar = (Map.Entry)cheapestCarsIterator.next();
            carObject = ((Car)cheapestCar.getValue());
            // only includes cars which have enough seats for passengers
            if (noOfPassengers <= carObject.getPassengers()) {
                // adds cars to carList
                carList.add(carObject);
            }
        }

        // json array that will store cars in json format
        JSONArray jsonCheapestCars = new JSONArray();
        for (Car car:carList) {
            // converts car into json object to and adds to json array
            putCars(jsonCheapestCars, car);
        }

        // returns json output for cheapest cars
        return new ResponseEntity(jsonCheapestCars.toString(), HttpStatus.OK);
    }

    /***
     * takes input through rest api and produces output of dave's taxis available cars through rest api
     * http://localhost:8080/rideways/daves-taxis - uses default data
     * http://localhost:8080/rideways/daves-taxis?pickup=51.470020,-0.454295&dropoff=51.470020,-0.454295&noOfPassengers=1
     * @param pickup pickup parameter taken from rest api
     * @param dropoff dropoff parameter taken from rest api
     * @param noOfPassengers noOfPassengers parameter taken from test api
     * @return success: json data of dave's taxis cars, fail: error message
     * @throws JSONException
     */
    @RequestMapping(value = "/daves-taxis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> searchDavesTaxis(@RequestParam(value = "pickup", defaultValue = "51.470020,-0.454295") String pickup,
                                                 @RequestParam(value = "dropoff", defaultValue = "51.470020,-0.454295") String dropoff,
                                                 @RequestParam(value = "noOfPassengers", defaultValue = "0") int noOfPassengers) throws JSONException {
        // clears old search data
        RidewaysApp.carList.clear();

        // initialise variables
        String url;
        int status;

        // builds url calls dave taxi's api and retrieves status
        url = "https://techtest.rideways.com/dave" + "?pickup=" + pickup + "&dropoff=" + dropoff;
        status = callTaxiAPI("Dave's Taxis", url);

        // get list of available cars and store in carList
        List<Car> carList = RidewaysApp.carList;

        // if status is erroneous or if no available cars then error messages produced
        if (status == 400){
            return new ResponseEntity("Please enter pickup and dropoff parameters correctly", HttpStatus.OK);
        } else if (status == 500) {
            return new ResponseEntity("Something went wrong", HttpStatus.OK);
        } else if (carList.isEmpty()) {
            return new ResponseEntity("No available cars", HttpStatus.OK);
        }

        // json array that will store cars in json format
        JSONArray jsonDavesTaxis = new JSONArray();
        for (Car car:carList) {
            if (noOfPassengers <= car.getPassengers()) {
                // converts car into json object to and adds to json array
                putCars(jsonDavesTaxis, car);
            }
        }

        // returns json output for dave's taxis cars
        return new ResponseEntity(jsonDavesTaxis.toString(), HttpStatus.OK);
    }

    /***
     * extracts car data and creates json object of car which is added to json array
     * @param jsonCars json array to store car data
     * @param car car to be stored in json array
     * @throws JSONException
     */
    private void putCars(JSONArray jsonCars, Car car) throws JSONException {
        // creates new json object
        JSONObject jsonCar = new JSONObject();
        // adds car data to json object
        jsonCar.put("carType", car.getCarType());
        jsonCar.put("supplier", car.getSupplier());
        jsonCar.put("price", car.getPrice());
        // adds json object to jon array
        jsonCars.put(jsonCar);
    }


}
