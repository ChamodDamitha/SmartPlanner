package com.example.chamod.smartplanner.Models.Tasks;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeSet;

/**
 * Created by chamod on 2/25/17.
 */

public class FullTask extends Task {
    private Location location;
    private TimeSet timeSet;

    public FullTask(String description, Date date, Location location,TimeSet timeSet) {
        super(description, date);

        this.location=location;
        this.timeSet=timeSet;
        this.type="FULL";
    }

    public Location getLocation() {
        return location;
    }

    public TimeSet getTimeSet() {
        return timeSet;
    }
}
