package com.example.chamod.smartplanner.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chamod on 2/25/17.
 */

public class Location {
    private int loc_id;
    private String name;
    private double latitude;
    private double longitude;
    private float range;

    public Location(String name, double latitude, double longitude,float range) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.range=range;
    }

    public JSONObject toJSON(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name", this.name);
            jsonObject.put("latitude", this.latitude);
            jsonObject.put("longitude", this.longitude);
            jsonObject.put("range", this.range);
            return jsonObject;
        }
        catch (JSONException e){
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getRange() {
        return range;
    }

    public int getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }
}
