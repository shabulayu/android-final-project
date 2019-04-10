package com.example.finalproject.Flight.model;


/**
 * author: Danyao Wang
 * version: 0.0.2
 * description: model of Flight
 */
public class Flight {

    private String location;
    private String altitude;
    private String speed;
    private String status;
    private String iataNumber;

    private String latitude;
    private String longitude;

    public Flight(String latitude, String longitude, String altitude, String speed, String status, String iataNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.status = status;
        this.iataNumber = iataNumber;
    }

    public Flight(String location, String altitude, String speed, String status, String iataNumber) {
        this.location = location;
        this.altitude = altitude;
        this.speed = speed;
        this.status = status;
        this.iataNumber = iataNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
