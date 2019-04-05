package com.example.finalproject.Flight.model;


/**
 * author: Danyao Wang
 * version: 0.0.2
 * description: model of Flight
 */
public class Flight {

    private double location;
    private double altitude;
    private double speed;
    private String status;
    private String iataNumber;

    private double latitude;
    private double longitude;

    public Flight(double latitude, double longitude, double altitude, double speed, String status, String iataNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.status = status;
        this.iataNumber = iataNumber;
    }

    public Flight(double location, double altitude, double speed, String status, String iataNumber) {
        this.location = location;
        this.altitude = altitude;
        this.speed = speed;
        this.status = status;
        this.iataNumber = iataNumber;
    }

    public double getLocation() {
        return location;
    }

    public void setLocation(double location) {
        this.location = location;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIataNumber() {
        return iataNumber;
    }

    public void setIataNumber(String iataNumber) {
        this.iataNumber = iataNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
