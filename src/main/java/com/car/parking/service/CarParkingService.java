package com.car.parking.service;

import com.car.parking.model.Car;
import com.car.parking.model.CarProperties;
import com.car.parking.repo.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class CarParkingService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarProperties carProperties;

    Random random = new Random();
    final List<Integer> numbers = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

    //private final long totalSpaces = 100;

    ReadWriteLock lock = new ReentrantReadWriteLock();
    // ...
    Lock writeLock = lock.writeLock();
    Lock readLock = lock.readLock();

    public long getAvailableSpaces() {
        try {
            readLock.lock();
            long spaces = carProperties.getTotalCount() - carRepository.countByInParking(true);
            log.info("Available spaces {}", spaces);
            return spaces;
        }
        finally {
            readLock.unlock();
        }
    }

    public List<Car> getCarAudit(String reg) {
        log.info("Finding a Car with reg {} ", reg);
        List<Car> cars = carRepository.findByReg(reg);
        if(cars.isEmpty()) {
            log.error("Unable to find a Car with Reg {} ", reg);
            throw new CarAuditException(reg);
        }
        return cars;
    }

    public Car getCar(String reg) {
        log.info("Finding a Car with reg {} ", reg);
        Car car = carRepository.findByRegAndInParking(reg, true);
        if (car == null){
            log.error("Unable to find a Car with Reg {} ", reg);
            throw new CarNotFoundException(reg);
        }
        return car;
    }

    public  void checkIn(Car car) {
        try {
            writeLock.lock();
            log.info("Finding a Car with reg {} ", car.getReg());
            Car existingCar = carRepository.findByRegAndInParking(car.getReg(), true);
            if (existingCar != null) {
                log.error("Car already parked with Reg {} ", car.getReg());
                throw new CarAllReadyExistException(car.getReg());
            }
            if (getAvailableSpaces() == 0) {
                throw new NoSpacesException();
            }
            car.setId(Math.abs(random.nextInt()));
            car.setCheckedIn(new Date());
            car.setInParking(true);
            car.setSpaceAllocated(numbers.get(0));
            log.info("Saving the Car with reg {} ", car.getReg());
            carRepository.save(car);
            log.info("Removing Space from the Pool {} ", numbers.get(0));
            removeTheSpace();
        }
        finally {
            writeLock.unlock();
        }
    }


    public double checkOut(String reg) {
        try {
            writeLock.lock();
            log.info("Finding a Car with reg {} ", reg);
            Car foundCar = carRepository.findByRegAndInParking(reg, true);
            if (foundCar == null) {
                log.error("Car not found with Reg {} ", reg);
                throw new CarNotFoundException(reg);
            }
            Car car = new Car();
            car.setReg(reg);
            car.setId(foundCar.getId());
            car.setCheckOut(new Date());
            car.setInParking(false);
            car.setCheckedIn(foundCar.getCheckedIn());
            log.info("Adding Space back to the Pool {} ", foundCar.getSpaceAllocated());
            addTheSpace(foundCar.getSpaceAllocated());
            log.info("Updating the Car with reg {} ", reg);
            carRepository.save(car);
            return calculatePrice(car.getCheckedIn(), car.getCheckOut());
        }
        finally {
            writeLock.unlock();
        }

    }

    public void removeTheSpace() {
        numbers.remove(0);
    }

    public void addTheSpace(int space) {
        numbers.add(0, space);
    }

    public double calculatePrice(Date checkInDate, Date checkOutDate) {

        Instant instant1 = checkInDate.toInstant();
        Instant instant2 = checkOutDate.toInstant();
        long minutes = ChronoUnit.MINUTES.between(instant1, instant2);
        long hours = ChronoUnit.HOURS.between(instant1, instant2);
        if (minutes % 60 > 0) {
            hours++;
        }

        return Math.round((hours * 2) * 100.0) / 100.0;
    }


    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().forEach(cars::add);
        return cars;
    }
}
