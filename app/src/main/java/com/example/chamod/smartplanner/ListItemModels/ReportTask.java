package com.example.chamod.smartplanner.ListItemModels;

/**
 * Created by chamod on 4/21/17.
 */

public class ReportTask {
    private String desc;
    private boolean completed;
    private boolean firstOne=false;


    public ReportTask(String desc, boolean completed) {
        this.desc = desc;
        this.completed = completed;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isFirstOne() {
        return firstOne;
    }

    public void setFirstOne(boolean firstOne) {
        this.firstOne = firstOne;
    }
}
