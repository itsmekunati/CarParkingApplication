package com.car.parking.service;

import com.car.parking.model.Car;
import com.car.parking.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

@Service
public class CarParkingService {

    @Autowired
    CarRepository carRepository;
    Random random = new Random();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final long totalSpaces = 100;

    public long getAvailableSpaces() {
        return (totalSpaces - carRepository.countByInParking(true));
    }

    public void checkIn(Car car) {
        Car existingCar = carRepository.findByRegAndInParking(car.getReg(), true);
        if (existingCar != null) {
            throw new CarAllReadyExistException(car.getReg());
        }
        if (getAvailableSpaces() == 0) {
            throw new NoSpacesException();
        }
        car.setId(Math.abs(random.nextInt()));
        car.setCheckedIn(new Date());
        car.setInParking(true);
        carRepository.save(car);
    }

    public double checkOut(Car car) {

        Car foundCar = carRepository.findByRegAndInParking(car.getReg(), true);
        if (foundCar == null) {
            throw new CarNotFoundException(car.getReg());
        }
        car.setId(foundCar.getId());
        car.setCheckOut(new Date());
        car.setInParking(false);
        car.setCheckedIn(foundCar.getCheckedIn());
        carRepository.save(car);
        Date checkInDate = car.getCheckedIn();
        Date checkOutDate = car.getCheckOut();
        Instant instant1 = checkInDate.toInstant();
        Instant instant2 = checkOutDate.toInstant();
        long minutes = ChronoUnit.MINUTES.between(instant1, instant2);
        long hours = ChronoUnit.HOURS.between(instant1, instant2);
        if (minutes % 60 > 0) {
            hours++;
        }

        return Math.round((hours * 2) * 100.0) / 100.0;
    }
}
