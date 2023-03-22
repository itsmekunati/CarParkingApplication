package com.car.parking.service;

public class CarNotFoundException extends RuntimeException {


    public CarNotFoundException(String reg) {
        super("Car not in Parking with reg " + reg + ".");
    }
}