package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 2/25/17.
 */

public class LocationTask extends Task {
    private Location location;
    private float range;

    public LocationTask(int id, String description, Date date, Location location, float range) {
        super(id, description, date);
        this.location=location;
        this.range=range;
        this.type="LOCATION";
    }

    public Location getLocation() {
        return location;
    }

    public float getRange() {
        return range;
    }
}
