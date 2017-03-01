package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 2/25/17.
 */

public class Date {
    private int day;
    private int month;
    private int year;
    private String dayOfWeek;
    private String monthOfyear;

    public Date(int day, int month, int year, String dayOfWeek) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayOfWeek = dayOfWeek;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getMonthOfYear(){
        String[] months={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        monthOfyear=months[month-1];
        return monthOfyear;
    }

    public String getDateString(){
        return dayOfWeek+", "+day+" "+getMonthOfYear()+" "+year;
    }
}
