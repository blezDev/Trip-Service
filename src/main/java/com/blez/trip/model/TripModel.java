package com.blez.trip.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TripModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trip_id;
    private String c_userId;
    private String c_name;
    private String c_email;
    private String rideSource;
    private String rideDestination;
    private String rideDate;
    private String fare;
    private String carName;
    private String carNumber;
    private String r_email;
    private String r_userId;


    public TripModel(String c_userId, String c_name, String c_email, String rideSource, String rideDestination, String rideDate, String fare, String carName, String carNumber, String r_email, String r_userId) {
        this.c_userId = c_userId;
        this.c_name = c_name;
        this.c_email = c_email;
        this.rideSource = rideSource;
        this.rideDestination = rideDestination;
        this.rideDate = rideDate;
        this.fare = fare;
        this.carName = carName;
        this.carNumber = carNumber;
        this.r_email = r_email;
        this.r_userId = r_userId;
    }
}
