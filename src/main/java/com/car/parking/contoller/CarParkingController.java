package com.car.parking.contoller;

import com.car.parking.model.Car;
import com.car.parking.service.CarParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarParkingController {

    @Autowired
    CarParkingService carParkingService;

    @GetMapping("/carAudit")
    public ResponseEntity<List<Car>> getCarAudit(@RequestParam String reg){
        List<Car> cars = carParkingService.getCarAudit(reg);
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping("/car")
    public ResponseEntity<Car> getCar(@RequestParam String reg){
        Car car = carParkingService.getCar(reg);
        return ResponseEntity.ok().body(car);
    }

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
    public ResponseEntity<Double> checkOut(@RequestParam String reg){
        double charges = carParkingService.checkOut(reg);
        return new ResponseEntity<>(charges ,HttpStatus.OK) ;

    }
}
