package com.car.parking.contoller;

import com.car.parking.model.Car;
import com.car.parking.model.CarDetails;
import com.car.parking.service.CarParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api")
public class CarParkingController {

    private CarParkingService carParkingService;
    CarParkingController(CarParkingService carParkingService){
        this.carParkingService = carParkingService;
    }

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
    public ResponseEntity<List<CarDetails>> getCars(){
        log.info("Calling the getCar Service to get Car Audit Details");
        List<Car> cars = carParkingService.getAllCars();
        List<CarDetails>  carsDetails = cars
                .stream()
                .filter(Car::isInParking)
                .map(t -> new CarDetails(t.getReg(), t.getCheckedIn(), t.getSpaceAllocated())).toList();
        return ResponseEntity.ok().body(carsDetails);
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
