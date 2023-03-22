package com.car.parking.service;

public class CarAllReadyExistException extends RuntimeException {


    public CarAllReadyExistException(String reg) {
        super("Car already in Parking with reg " + reg + ".");
    }
}