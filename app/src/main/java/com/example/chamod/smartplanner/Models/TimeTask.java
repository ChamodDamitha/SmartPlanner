package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 2/25/17.
 */

public class TimeTask extends Task {
    private Time time;
    private Time alert_time;

    public TimeTask(int id, String description, Date date, Time time,Time alert_time) {
        super(id, description, date);
        this.time=time;
        this.alert_time=alert_time;
    }

    public Time getTime() {
        return time;
    }

    public Time getAlert_time() {
        return alert_time;
    }
}
