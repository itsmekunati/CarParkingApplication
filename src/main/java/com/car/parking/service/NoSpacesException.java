package com.car.parking.service;

public class NoSpacesException extends RuntimeException {


    public NoSpacesException() {
        super("No Spaces Available");
    }
}