
package com.car.parking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Table
@Entity
public class Car {

    @Id
    @Column
    private int id;

    @Column
    private String reg;

    @Column
    private Date checkedIn;

    @Column
    private Date checkOut;

    @Column
    private boolean inParking;
}
