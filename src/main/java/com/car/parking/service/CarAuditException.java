package com.car.parking.service;

public class CarAuditException extends RuntimeException {


    public CarAuditException(String reg) {
        super("Car never used this Parking Spaces with " + reg + ".");
    }
}