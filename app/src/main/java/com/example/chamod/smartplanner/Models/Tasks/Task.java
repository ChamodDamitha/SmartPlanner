package com.example.chamod.smartplanner.Models.Tasks;

import com.example.chamod.smartplanner.Models.Date;

import org.json.JSONObject;

/**
 * Created by chamod on 2/25/17.
 */

public abstract class Task {
    private int id;
    private String description;
    private Date date;
    private boolean alerted=false;
    private boolean completed=false;
    private boolean repeat=false;

    protected String type;

    public Task(String description, Date date) {
        this.description = description;
        this.date = date;
    }


    public abstract JSONObject toJSON();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public boolean isAlerted() {
        return alerted;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getType(){
        return type;
    }

    public void setAlerted(boolean alerted) {
        this.alerted = alerted;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
}
