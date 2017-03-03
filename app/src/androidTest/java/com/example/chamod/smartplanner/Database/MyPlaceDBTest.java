package com.example.chamod.smartplanner.Database;

import android.support.test.InstrumentationRegistry;

import com.example.chamod.smartplanner.Models.MyPlace;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chamod on 3/3/17.
 */
public class MyPlaceDBTest {
    MyPlaceDB myPlaceDB;

    @Test
    public void addMyPlace() throws Exception {
        myPlaceDB=MyPlaceDB.getInstance(InstrumentationRegistry.getTargetContext());

        MyPlace myPlace=new MyPlace("test_name","test_address",4.569,4.555);

        myPlaceDB.addMyPlace(myPlace);

//        check if place name taken
        assertEquals(true,myPlaceDB.isPlaceNameTaken("test_name"));

//        check getMyPlace
        MyPlace myPlace1=myPlaceDB.getMyPlace("test_name");

        assertEquals(myPlace.getName(),myPlace1.getName());
        assertEquals(myPlace.getAddress(),myPlace1.getAddress());
        assertEquals(myPlace.getLatitude(),myPlace1.getLatitude(),0.1);
        assertEquals(myPlace.getLongitude(),myPlace1.getLongitude(),0.1);

//        check getAllMyPlaces
        assertEquals(1,myPlaceDB.getAllMyPlaces().size());
    }


}