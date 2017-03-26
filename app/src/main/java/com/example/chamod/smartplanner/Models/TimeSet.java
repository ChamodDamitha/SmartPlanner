package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 3/26/17.
 */

public class TimeSet {
    private int time_id;
    private Date alert_date;
    private Time alert_time,task_time;

    public TimeSet(Date alert_date, Time alert_time, Time task_time) {
        this.alert_date = alert_date;
        this.alert_time = alert_time;
        this.task_time = task_time;
    }

    public int getTimeset_id() {
        return time_id;
    }

    public Date getAlert_date() {
        return alert_date;
    }

    public Time getAlert_time() {
        return alert_time;
    }

    public Time getTask_time() {
        return task_time;
    }

    public void setTimeset_id(int time_id) {
        this.time_id = time_id;
    }
}
