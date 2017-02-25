package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 2/25/17.
 */

public class FullTask extends Task {
    private Location location;
    private double range;
    private Time time;
    private Time alert_time;

    public FullTask(int id, String description, Date date, Location location, double range, Time time,Time alert_time) {
        super(id, description, date);

        this.location=location;
        this.range=range;
        this.time=time;
        this.alert_time=alert_time;
    }

    public Location getLocation() {
        return location;
    }

    public double getRange() {
        return range;
    }

    public Time getTime() {
        return time;
    }

    public Time getAlert_time() {
        return alert_time;
    }
}
