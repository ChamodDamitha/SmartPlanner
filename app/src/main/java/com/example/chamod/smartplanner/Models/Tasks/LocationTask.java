package com.example.chamod.smartplanner.Models.Tasks;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Location;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("task_id",this.getId());
            jsonObject.put("description",this.getDescription());
            jsonObject.put("date",this.getDate().toString());
            jsonObject.put("location",this.getLocation().toJSON());
            jsonObject.put("completed",this.isCompleted());
            jsonObject.put("repeated",this.isRepeat());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
