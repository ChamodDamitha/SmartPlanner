package com.example.chamod.smartplanner.Models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chamod on 2/26/17.
 */
public class DateTest {
    @Test
    public void getDayOfWeek() throws Exception {
        Date date1=new Date(26,2,2017);
        Date date2=new Date(3,3,2017);
        Date date3=new Date(29,2,2016);

        assertEquals("SUN",date1.getDayOfWeek());
        assertEquals("FRI",date2.getDayOfWeek());
        assertEquals("MON",date3.getDayOfWeek());

    }

    @Test
    public void getMonthOfYear() throws Exception {
        String output,expect="FEB";

        Date date=new Date(26,2,2017);

        output=date.getMonthOfYear();

        assertEquals(expect,output);

    }

}