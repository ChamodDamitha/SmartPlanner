package com.example.chamod.smartplanner.Handlers;

import android.content.Context;

import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Models.Time;

import java.util.Date;

/**
 * Created by chamod on 3/24/17.
 */

public class TimeHandler {
    private static TimeHandler timeHandler = null;
    private TaskDB taskDB;

    private TimeHandler() {
    }

    public static TimeHandler getInstance() {
        if (timeHandler == null) {
            timeHandler = new TimeHandler();
        }
        return timeHandler;
    }


    public Time getAlertTime(Time time,long before_miliseconds){

        return null;
    }

}
