package com.example.chamod.smartplanner.Models.Tasks;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeSet;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("task_id",this.getId());
            jsonObject.put("description",this.getDescription());
            jsonObject.put("date",this.getDate().toString());
            jsonObject.put("time",this.getTimeSet().getTask_time());
            jsonObject.put("completed",this.isCompleted());
            jsonObject.put("repeated",this.isRepeat());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
