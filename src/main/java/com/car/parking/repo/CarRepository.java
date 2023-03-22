package com.car.parking.repo;

import com.car.parking.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Integer> {

   /* @Query(value = "SELECT * FROM CAR WHERE reg=? WHERE IN_PARKING = true",nativeQuery = true)
    public Car findByReg(String reg);*/

    Car findByRegAndInParking(String reg, boolean in_parking);

    int countByInParking(boolean in_parking);
}
