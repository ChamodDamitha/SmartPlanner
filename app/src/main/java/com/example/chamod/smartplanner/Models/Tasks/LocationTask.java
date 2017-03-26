package com.example.chamod.smartplanner.Models.Tasks;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Location;

/**
 * Created by chamod on 2/25/17.
 */

public class LocationTask extends Task {
    private Location location;

    public LocationTask(String description, Date date, Location location) {
        super(description, date);
        this.location=location;
        this.type="LOCATION";
    }

    public Location getLocation() {
        return location;
    }

}
