package com.example.chamod.smartplanner.Models.Tasks;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeSet;

/**
 * Created by chamod on 2/25/17.
 */

public class TimeTask extends Task {

    private TimeSet timeSet;

    public TimeTask(String description, Date date, TimeSet timeSet) {
        super(description, date);
        this.timeSet=timeSet;
        this.type="TIME";
    }

    public TimeSet getTimeSet() {
        return timeSet;
    }
}
