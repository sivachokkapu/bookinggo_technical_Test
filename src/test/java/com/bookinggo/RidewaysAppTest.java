package com.bookinggo;

import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.bookinggo.RidewaysApp.*;
import static org.junit.jupiter.api.Assertions.*;


class RidewaysAppTest {

    @After
    public void TearDown(){
        carList.clear();
        cheapestCars.clear();
    }

    /***
     * Checks for correct outcome when valid data is used for selecting cheapesr cars
     * should result in cheapestCars hashMap being populated
     */
    @Test
    public void chooseCheapestSuppliersTest() {
        // Arrange
        List<Car> testCarList = new ArrayList<>();
        testCarList.add(new Car("dave", "STANDARD", 232323));
        testCarList.add(new Car("eric", "STANDARD", 131313));
        carList = testCarList;
        // Act
        chooseCheapestSuppliers();
        // Assert
        Assert.assertFalse(cheapestCars.isEmpty());
    }

    /***
     * Checks for correct outcome when no data is used for selecting cheapest cars
     * should result in cheapestCars hashMap being empty
     */
    @Test
    public void chooseCheapestSuppliersTestEmptyMap() {
        // Arrange
        List<Car> testCarList = new ArrayList<>();
        carList = testCarList;
        // Act
        chooseCheapestSuppliers();
        // Assert
        Assert.assertTrue(cheapestCars.isEmpty());
    }

}