package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 2/25/17.
 */

public class TimeTask extends Task {
    private Time time;
    private Time alert_time;
    private Date alert_date;

    public TimeTask(int id, String description, Date date, Time time,Date alert_date,Time alert_time) {
        super(id, description, date);
        this.time=time;
        this.alert_time=alert_time;
        this.alert_date=alert_date;
        this.type="TIME";
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
