package com.example.chamod.smartplanner.Calculaters;

import com.example.chamod.smartplanner.Models.Time;

/**
 * Created by chamod on 3/23/17.
 */

public class TimeCompare {

//    return true if t1 earlier than t2
    public static boolean compareTime(Time t1,Time t2){
        if(t1.get24Hour()<t2.get24Hour()){
            return true;
        }
        else if(t1.get24Hour()==t2.get24Hour()){
            if (t1.getMinute() < t2.getMinute()) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
