package com.car.parking.contoller;

import com.car.parking.model.Car;
import com.car.parking.service.CarParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarParkingController {

    @Autowired
    CarParkingService carParkingService;

    @GetMapping("/getSpaces")
    public long getSpaces() {
        return  carParkingService.getAvailableSpaces();
    }

    @PostMapping("/checkIn")
    public ResponseEntity<Car> checkIn(@RequestBody Car car){
        carParkingService.checkIn(car);
        return ResponseEntity.ok().body(car);
    }

    @PostMapping("/checkOut")
    public ResponseEntity<Double> checkOut(@RequestBody Car car){
        double charges = carParkingService.checkOut(car);
        return new ResponseEntity<>(charges ,HttpStatus.OK) ;

    }
}
