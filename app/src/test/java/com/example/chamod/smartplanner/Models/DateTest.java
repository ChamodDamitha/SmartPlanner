package com.example.chamod.smartplanner.Models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chamod on 2/26/17.
 */
public class DateTest {
    @Test
    public void getMonthOfYear() throws Exception {
        String output,expect="FEB";

        Date date=new Date(26,2,2017,"SUN");

        output=date.getMonthOfYear();

        assertEquals(expect,output);

    }

}