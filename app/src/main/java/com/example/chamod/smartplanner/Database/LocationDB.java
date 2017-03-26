package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Message;

/**
 * Created by chamod on 3/26/17.
 */

public class LocationDB {
    private static LocationDB locationDB=null;

    private DB_Helper db_helper;

    private LocationDB(Context context){
        db_helper=DB_Helper.getInstance(context);
    }

    public static LocationDB getInstance(Context context){
        if(locationDB==null){
            locationDB=new LocationDB(context);
        }
        return locationDB;
    }

//    ...........Add a location record..............
    public Location addLocationRecord(Location location){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_location_name,location.getName());
        cv.put(DB_Helper.task_location_latitude,location.getLatitude());
        cv.put(DB_Helper.task_location_longitude,location.getLongitude());
        cv.put(DB_Helper.task_location_range,location.getRange());

        db.insert(DB_Helper.locations_table,null,cv);

        db=db_helper.getReadableDatabase();
        String query = String.format("SELECT MAX(%s) as max_id FROM %s ;",DB_Helper.loc_id,DB_Helper.locations_table);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            location.setLoc_id(cursor.getInt(cursor.getColumnIndex("max_id")));
            return location;
        }
        return null;
    }

    //    ...........Update a Location record..............
    public boolean updateLocationRecord(Location location){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_location_name,location.getName());
        cv.put(DB_Helper.task_location_latitude,location.getLatitude());
        cv.put(DB_Helper.task_location_longitude,location.getLongitude());
        cv.put(DB_Helper.task_location_range,location.getRange());
        db.update(DB_Helper.locations_table,cv,DB_Helper.loc_id+"="+location.getLoc_id(),null);

        return true;
    }

    //    ...............Get a Location record.................................
    public Location getLocationRecord(int loc_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s=%s;",DB_Helper.locations_table,DB_Helper.loc_id,loc_id);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Location location=new Location(cursor.getString(cursor.getColumnIndex(DB_Helper.task_location_name)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_latitude)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_longitude)),
                    cursor.getFloat(cursor.getColumnIndex(DB_Helper.task_location_range)));

            location.setLoc_id(loc_id);
        }
        return null;
    }


    //    ...........Delete a location record..............................
    public boolean deleteLocationRecord(int loc_id){
        SQLiteDatabase db=db_helper.getWritableDatabase();
        db.delete(DB_Helper.locations_table,DB_Helper.loc_id+"="+loc_id,null);
        return true;
    }
}
