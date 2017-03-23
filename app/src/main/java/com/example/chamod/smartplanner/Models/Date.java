package com.example.chamod.smartplanner.Models;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by chamod on 2/25/17.
 */

public class Date implements Serializable{
    private int day;
    private int month;
    private int year;
    private String monthOfyear;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
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
        String[] days={"MON","TUE","WED","THU","FRI","SAT","SUN"};

        GregorianCalendar gregorianCalendar=new GregorianCalendar(year,month-1,day-1);

        return days[gregorianCalendar.get(gregorianCalendar.DAY_OF_WEEK)-1];
    }

    public String getMonthOfYear(){
        String[] months={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        monthOfyear=months[month-1];
        return monthOfyear;
    }

    public String getDateString(){
        return getDayOfWeek()+", "+day+" "+getMonthOfYear()+" "+year;
    }
}
