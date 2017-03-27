package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeSet;

/**
 * Created by chamod on 3/26/17.
 */

public class TimesetDB {
    private static TimesetDB dateDB=null;

    private DB_Helper db_helper;

    private TimesetDB(Context context){
        db_helper=DB_Helper.getInstance(context);
    }

    public static TimesetDB getInstance(Context context){
        if(dateDB==null){
            dateDB=new TimesetDB(context);
        }
        return dateDB;
    }

//    ..........Add a time record..............................
    public boolean addTimeRecord(TimeSet timeSet){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.alert_date,timeSet.getAlert_date().toString());
        cv.put(DB_Helper.alert_time,timeSet.getAlert_time().toString());
        cv.put(DB_Helper.task_time,timeSet.getTask_time().toString());

        db.insert(DB_Helper.timeset_table,null,cv);

        db=db_helper.getReadableDatabase();
        String query = String.format("SELECT MAX(%s) as max_id FROM %s ;",DB_Helper.timeset_id,DB_Helper.timeset_table);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            timeSet.setTimeset_id(cursor.getInt(cursor.getColumnIndex("max_id")));
            return true;
        }
//        when no record in table
        return false;
    }

    //    ...........Update a Timeset record..............
    public boolean updateTimesetRecord(TimeSet timeSet){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.alert_date,timeSet.getAlert_date().toString());
        cv.put(DB_Helper.alert_time,timeSet.getAlert_time().toString());
        cv.put(DB_Helper.task_time,timeSet.getTask_time().toString());
        db.update(DB_Helper.timeset_table,cv,DB_Helper.timeset_id+"="+timeSet.getTimeset_id(),null);

        return true;
    }

    //    ...............Get a Timeset record.................................
    public TimeSet getTimesetRecord(int timeset_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s=%s;",DB_Helper.timeset_table,DB_Helper.timeset_id,timeset_id);
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToNext())
        {
            Date alert_date=new Date(cursor.getString(cursor.getColumnIndex(DB_Helper.alert_date)));
            Time alert_time=new Time(cursor.getString(cursor.getColumnIndex(DB_Helper.alert_time)));
            Time task_time=new Time(cursor.getString(cursor.getColumnIndex(DB_Helper.task_time)));

            return new TimeSet(alert_date,alert_time,task_time);

        }
        return null;
    }

//    ...........Delete a time record..............................
    public boolean deleteTimeRecord(int timeset_id){
        SQLiteDatabase db=db_helper.getWritableDatabase();
        db.delete(DB_Helper.timeset_table,DB_Helper.timeset_id +"="+timeset_id,null);
        return true;
    }
}