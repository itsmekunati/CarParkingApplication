package com.car.parking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CarDetails {

    private String reg;
    private Date checkedIn;
    private int spaceAllocated;

}
