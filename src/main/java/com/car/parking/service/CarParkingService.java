package com.car.parking.service;

import com.car.parking.model.Car;
import com.car.parking.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CarParkingService {

    @Autowired
    CarRepository carRepository;
    Random random = new Random();
    final List<Integer> numbers = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

    private final long totalSpaces = 100;

    ReadWriteLock lock = new ReentrantReadWriteLock();
    // ...
    Lock writeLock = lock.writeLock();
    Lock readLock = lock.readLock();

    public long getAvailableSpaces() {
        try {
            readLock.lock();
            return (totalSpaces - carRepository.countByInParking(true));
        }
        finally {
            readLock.unlock();
        }
    }

    public List<Car> getCarAudit(String reg) {
        List<Car> cars = carRepository.findByReg(reg);
        if(cars.isEmpty()) {
            throw new CarAuditException(reg);
        }
        return cars;
    }

    public Car getCar(String reg) {
        Car car = carRepository.findByRegAndInParking(reg, true);
        if (car == null){
            throw new CarNotFoundException(reg);
        }
        return car;
    }

    public  void checkIn(Car car) {
        try {
            writeLock.lock();
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
            car.setSpaceAllocated(numbers.get(0));
            carRepository.save(car);
            removeTheSpace();
        }
        finally {
            writeLock.unlock();
        }
    }


    public double checkOut(String reg) {
        try {
            writeLock.lock();
            Car foundCar = carRepository.findByRegAndInParking(reg, true);
            if (foundCar == null) {
                throw new CarNotFoundException(reg);
            }
            Car car = new Car();
            car.setReg(reg);
            car.setId(foundCar.getId());
            car.setCheckOut(new Date());
            car.setInParking(false);
            car.setCheckedIn(foundCar.getCheckedIn());
            addTheSpace(foundCar.getSpaceAllocated());
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


}
