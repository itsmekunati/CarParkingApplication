package com.car.parking.contoller;

import com.car.parking.model.Car;
import com.car.parking.service.CarParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class CarParkingController {

    @Autowired
    CarParkingService carParkingService;

    @GetMapping("/carAudit")
    public ResponseEntity<List<Car>> getCarAudit(@RequestParam String reg){
        log.info("Calling the getCarAudit Service to get Full Car Audit Details");
        List<Car> cars = carParkingService.getCarAudit(reg);
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping("/car")
    public ResponseEntity<Car> getCar(@RequestParam String reg){
        log.info("Calling the getCar Service to get Car Audit Details");
        Car car = carParkingService.getCar(reg);
        return ResponseEntity.ok().body(car);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCars(){
        log.info("Calling the getCar Service to get Car Audit Details");
        List<Car> cars = carParkingService.getAllCars();
        return ResponseEntity.ok().body(cars);
    }
    @GetMapping("/getSpaces")
    public long getSpaces() {
        log.info("Calling the getAvailableSpaces Service to get available spaces information");
        return  carParkingService.getAvailableSpaces();
    }

    @PostMapping("/checkIn")
    public ResponseEntity<Car> checkIn(@RequestBody Car car){
        log.info("Checking In the Car to the Register with reg {} ", car.getReg());
        carParkingService.checkIn(car);
        return ResponseEntity.ok().body(car);
    }

    @PostMapping("/checkOut")
    public ResponseEntity<Double> checkOut(@RequestParam String reg){
        log.info("Checking Out the Car to the Register with reg {} ", reg);
        double charges = carParkingService.checkOut(reg);
        return new ResponseEntity<>(charges ,HttpStatus.OK) ;

    }
}
