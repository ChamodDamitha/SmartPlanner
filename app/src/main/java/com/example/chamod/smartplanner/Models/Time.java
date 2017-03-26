package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 2/25/17.
 */

public class Time {
    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int get24Hour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getAmOrPM(){
        if(hour>=12){
            return "PM";
        }
        return "AM";
    }

    public int get12Hour(){
        if(hour>12){
            return hour-12;
        }
        return hour;
    }

    public String getTimeString(){
        String time_string=get12Hour()+":";
        if(minute<10){
            time_string+=0;
        }
        time_string+=minute+" "+getAmOrPM();

        return time_string;
    }

    @Override
    public String toString() {
        String time_string=get24Hour()+":";
        if(minute<10){
            time_string+=0;
        }
        time_string+=minute;
        return time_string;
    }
}
