package com.car.parking.config;

import com.car.parking.model.CarProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CarProperties.class)
public class RootConfig {

    // implement here
}
