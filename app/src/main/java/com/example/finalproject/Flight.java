package com.example.finalproject;

public class Flight {

    private String airportCode;
    private long id;
    private Role role;

    Flight(long id, String airportCode, Role role){
        this.airportCode = airportCode;
        this.id =id;
        this.role = role;
    }

    public String getAirportCode(){
        return airportCode;
    }

    public Flight setAirportCode(String airportCode){
        this.airportCode =airportCode;
        return this;
    }

    public long getId(){
        return id;
    }
    public Flight setId() {
        this.id = id;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    enum Role {
        Departure, Arrival
    }
   /* private String location;
    private double speed;
    private double attitude;
    private String status;
    private long id;

    public String getLocation(){
        return location;
    }

    public Flight setLocation(String location){
        this.location = location;
        return this;
    }

    public double getSpeed(){
        return speed;
    }

    public Flight setSpeed(double speed){
        this.speed = speed;
        return this;
    }

    public double getAltitude(){
        return attitude;
    }

    public Flight setAttitude(double attitude){
        this.attitude=attitude;
        return this;
    }

    public String getStatus(){
        return status;
    }

    public Flight setStatus(){
        this.status = status;
        return this;
    }

    public long getId(){
        return id;
    }
    public Flight setId(){
        this.id = id;
        return this;
    }*/
}
