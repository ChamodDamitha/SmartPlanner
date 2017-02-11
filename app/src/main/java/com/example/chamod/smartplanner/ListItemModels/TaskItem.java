package com.example.chamod.smartplanner.ListItemModels;

/**
 * Created by chamod on 2/11/17.
 */

public class TaskItem {
    private String description;
    private String time;
    private String location;

    public TaskItem(String description, String time, String location) {
        this.description = description;
        this.time = time;
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }
}
