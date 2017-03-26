package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Time;

/**
 * Created by chamod on 3/26/17.
 */

public class TimeDB {
    private static TimeDB dateDB=null;

    private DB_Helper db_helper;

    private TimeDB(Context context){
        db_helper=DB_Helper.getInstance(context);
    }

    public static TimeDB getInstance(Context context){
        if(dateDB==null){
            dateDB=new TimeDB(context);
        }
        return dateDB;
    }

//    ..........Add a time record..............................
    public int addTimeRecord(Date alert_date, Time alert_time,Time task_time){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.date_alert,alert_date.toString());
        cv.put(DB_Helper.time_alert,alert_time.toString());
        cv.put(DB_Helper.task_time,task_time.toString());

        db.insert(DB_Helper.times_table,null,cv);

        db=db_helper.getReadableDatabase();
        String query = String.format("SELECT MAX(%s) as max_id FROM %s ;",DB_Helper.time_id,DB_Helper.times_table);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            return cursor.getInt(cursor.getColumnIndex("max_id"));
        }
//        when no record in table
        return -1;
    }

//    ...........Delete a time record..............................
    public boolean deleteTimeRecord(int time_id){
        SQLiteDatabase db=db_helper.getWritableDatabase();
        db.delete(DB_Helper.times_table,DB_Helper.time_id+"="+time_id,null);
        return true;
    }
}
