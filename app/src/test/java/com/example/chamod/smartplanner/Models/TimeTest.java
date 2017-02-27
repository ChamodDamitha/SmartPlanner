package com.example.chamod.smartplanner.Models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chamod on 2/26/17.
 */
public class TimeTest {
    @Test
    public void getTimeString() throws Exception {
        String output,expect="2:37 PM";

        Time time=new Time(14,37);
        output=time.getTimeString();

        assertEquals(expect,output);
    }

    @Test
    public void get24Hour() throws Exception {
        int output,expect=14;

        Time time=new Time(14,37);
        output=time.get24Hour();

        assertEquals(expect,output);
    }

    @Test
    public void getMinute() throws Exception {
        int output,expect=37;

        Time time=new Time(14,37);
        output=time.getMinute();

        assertEquals(expect,output);
    }

    @Test
    public void getAmOrPM() throws Exception {
        String output,expect="PM";

        Time time=new Time(14,37);
        output=time.getAmOrPM();

        assertEquals(expect,output);
    }

    @Test
    public void get12Hour() throws Exception {
        int output,expect=2;

        Time time=new Time(14,37);
        output=time.get12Hour();

        assertEquals(expect,output);
    }

}