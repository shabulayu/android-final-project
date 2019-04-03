package com.example.finalproject.Flight.model;


/**
 * author: Danyao Wang
 * version: 0.0.2
 * description: model of Flight
 */
public class Flight {

    private double latitude;
    private double longitude;
    private double altitude;
    private double horizontal;
    private String status;
    private String iataNumber;

    public Flight(double latitude, double longitude, double altitude, double horizontal, String status, String iataNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.horizontal = horizontal;
        this.status = status;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
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
}
