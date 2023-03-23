package com.car.parking.repo;

import com.car.parking.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Integer> {

    Car findByRegAndInParking(String reg, boolean in_parking);

    int countByInParking(boolean in_parking);

    List<Car> findByReg(String reg);
}
