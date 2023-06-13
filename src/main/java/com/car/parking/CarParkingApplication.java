package com.car.parking;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class CarParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarParkingApplication.class, args);
	}

}
