
package com.car.parking.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "cars")
public class Car {

    @Id
    private int id;
    private String reg;
    private Date checkedIn;
    private int spaceAllocated;
    private Date checkOut;
    private boolean inParking;
}
