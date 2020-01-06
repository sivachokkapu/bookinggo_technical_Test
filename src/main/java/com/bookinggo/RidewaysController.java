package com.bookinggo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;

/***
 * controls data input and output from taxi rest apis
 */
public class RidewaysController {
    // initialise variables
    private static int price;
    private static String carType;
    private static Car car;
    private static HttpURLConnection connection;


    /***
     * takes url and calls taxi api to retrieve json data
     * @param supplier supplier name
     * @param url url of desired taxi api
     * @return status number to determine if call was successful or resulted in error
     */
    public static int callTaxiAPI(String supplier, String url){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        int status = 0;

        try {
            URL urlAPI = new URL(url);
            connection = (HttpURLConnection) urlAPI.openConnection();

            // request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(5000);

            // determines status number
            status = connection.getResponseCode();

            if (status == 200) {
                // success so retrieves json data from api
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

                // calls parse method to extract data from json data
                parse(responseContent.toString(), supplier);
            } else if (status == 400) {
                // unsuccessful so outputs error message
                System.out.println("Required String parameter 'pickup' and/or 'dropoff' is not present");
            } else if (status == 500) {
                // unsuccessful so outputs error message
                System.out.println("Something went wrong");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        // returns status number
        return status;
    }

    /***
     * parses json data and retrieves car data and adds it to carList
     * @param responseBody json data
     * @param supplier car supplier
     * @throws JSONException
     */
    public static void parse(String responseBody, String supplier) throws JSONException {
        JSONObject body =  new JSONObject(responseBody);
        // retrieve available cars from taxi api
        JSONArray options = body.getJSONArray("options");

        // iterates through options to retrieve cars
        for (int i = 0; i < options.length(); i++) {
            // get json object
            JSONObject option = options.getJSONObject(i);
            // get car data from json object
            carType = option.getString("car_type");
            price = option.getInt("price");
            // create car object from retrieved data and add to carList
            car = new Car(supplier, carType, price);
            RidewaysApp.carList.add(car);
        }
    }
}
