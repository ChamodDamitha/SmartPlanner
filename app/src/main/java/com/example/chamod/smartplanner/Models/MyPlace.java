package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 3/3/17.
 */

public class MyPlace {
    private int place_id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    public MyPlace(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address=address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
