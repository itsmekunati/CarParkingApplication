package com.car.parking.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationException(CarNotFoundException e) {
        return new ResponseEntity<>("Car Not in Parking", HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationException(CarAuditException e) {
        return new ResponseEntity<>("Car Never used this Parking", HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationException(CarAllReadyExistException e) {
        return new ResponseEntity<>("Car Already in Parking", HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationException(NoSpacesException e) {
        return new ResponseEntity<>("No Spaces Available", HttpStatus.OK);
    }
}