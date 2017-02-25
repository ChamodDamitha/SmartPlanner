package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 2/25/17.
 */

public abstract class Task {
    private int id;
    private String description;
    private Date date;
    private boolean alerted=false;
    private boolean completed=false;

    public Task(int id, String description, Date date) {
        this.id = id;
        this.description = description;
        this.date = date;
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
}