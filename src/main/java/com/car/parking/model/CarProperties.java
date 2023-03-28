package com.car.parking.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("car")
@Setter
@Getter
public class CarProperties {

    private int totalCount;
}
