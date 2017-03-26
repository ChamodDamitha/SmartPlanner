package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 2/25/17.
 */

public class FullTask extends Task {
    private Location location;
    private float range;
    private Time time;
    private Time alert_time;
    private Date alert_date;

    public FullTask(int id, String description, Date date, Location location, float range, Time time,Date alert_date,Time alert_time) {
        super(id, description, date);

        this.location=location;
        this.range=range;
        this.time=time;
        this.alert_date=alert_date;
        this.alert_time=alert_time;
        this.type="FULL";
    }

    public Location getLocation() {
        return location;
    }

    public float getRange() {
        return range;
    }

    public Time getTime() {
        return time;
    }

    public Date getAlert_date() {
        return alert_date;
    }

    public Time getAlert_time() {
        return alert_time;
    }
}
