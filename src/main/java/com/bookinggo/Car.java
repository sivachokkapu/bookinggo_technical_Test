package com.bookinggo;

/***
 *  Represents a car from one of the suppliers
 */
public class Car {
    private String supplier;
    private String carType;
    private int passengers;
    private int price;


    /***
     * Constructor for Car class
     * @Param supplier the car's suppler to set
     * @Param carType the car's type to set
     * @{aram price the car's price to set
     */
    public Car(String supplier, String carType, int price) {
        this.supplier = supplier;
        this.carType = carType;
        this.price = price;

        // uses carType to determine number of passengers
        switch (carType) {
            case "STANDARD":
                this.passengers = 4;
                break;
            case "EXECUTIVE":
                this.passengers = 4;
                break;
            case "LUXURY":
                this.passengers = 4;
                break;
            case "PEOPLE_CARRIER":
                this.passengers = 6;
                break;
            case "LUXURY_PEOPLE_CARRIER":
                this.passengers = 6;
                break;
            case "MINIBUS":
                this.passengers = 16;
                break;
        }

    }

    /***
     *  getter that returns the car's supplier
     *  @Return supplier
     */
    public String getSupplier() {
        return supplier;
    }

    /***
     *  getter that returns the car's type
     *  @Return carType
     */
    public String getCarType() {
        return carType;
    }

    /***
     *  getter that returns the car's number of passengers
     *  @Return passengers
     */
    public int getPassengers() {
        return passengers;
    }

    /***
     *  getter that returns the car's price
     *  @Return price
     */
    public int getPrice() {
        return price;
    }


}
