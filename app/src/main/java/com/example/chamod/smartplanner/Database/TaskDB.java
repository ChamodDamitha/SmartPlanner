package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.LocationTask;
import com.example.chamod.smartplanner.Models.Task;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeTask;

import java.util.ArrayList;

/**
 * Created by chamod on 2/25/17.
 */

public class TaskDB {
    private static TaskDB taskDB=null;

    public static TaskDB getInstance(Context context){
        if(taskDB==null){
            taskDB=new TaskDB(context);
        }
        return taskDB;
    }

    private DB_Helper db_helper;

    private TaskDB(Context context){
        db_helper=DB_Helper.getInstance(context);
    }


    public int getNextTaskId(){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT MAX(%s) as max_id FROM %s ;",DB_Helper.task_id,DB_Helper.tasks_table);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            return cursor.getInt(cursor.getColumnIndex("max_id"))+1;
        }
//        when no record in table
        return 1;
    }


//    adding tasks
    public void addFullTask(FullTask fullTask){
        addTask(fullTask);

        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_id,fullTask.getId());

        cv.put(DB_Helper.date_alert,fullTask.getDate()toString());
        cv.put(DB_Helper.time_alert,fullTask.getTime(.toString());
        cv.put(DB_Helper.task_time,fullTask.getTime(.toString());

        cv.put(DB_Helper.task_location_name,fullTask.getLocation().getName());
        cv.put(DB_Helper.task_location_latitude,fullTask.getLocation().getLatitude());
        cv.put(DB_Helper.task_location_longitude,fullTask.getLocation().getLongitude());
        cv.put(DB_Helper.task_location_range,fullTask.getRange());


        db.insert(DB_Helper.full_task_table,null,cv);
    }

    public void addLocationTask(LocationTask locationTask) {
        addTask(locationTask);

        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_id,locationTask.getId());

        cv.put(DB_Helper.task_location_name,locationTask.getLocation().getName());
        cv.put(DB_Helper.task_location_latitude,locationTask.getLocation().getLatitude());
        cv.put(DB_Helper.task_location_longitude,locationTask.getLocation().getLongitude());
        cv.put(DB_Helper.task_location_range,locationTask.getRange());

        db.insert(DB_Helper.location_task_table,null,cv);
    }

    public void addTimeTask(TimeTask timeTask){
        addTask(timeTask);

        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_id,timeTask.getId());

        cv.put(DB_Helper.task_hour,timeTask.getTime().get24Hour());
        cv.put(DB_Helper.task_minute,timeTask.getTime().getMinute());
        cv.put(DB_Helper.task_alert_hour,timeTask.getAlert_time().get24Hour());
        cv.put(DB_Helper.task_alert_minute,timeTask.getAlert_time().getMinute());


        db.insert(DB_Helper.full_task_table,null,cv);
    }

    private void addTask(Task task){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_id,task.getId());
        cv.put(DB_Helper.task_description,task.getDescription());
        cv.put(DB_Helper.task_year,task.getDate().getYear());
        cv.put(DB_Helper.task_month,task.getDate().getMonth());
        cv.put(DB_Helper.task_day,task.getDate().getDay());

        if(task.isAlerted()) {
            cv.put(DB_Helper.task_alerted, 1);
        }
        else {
            cv.put(DB_Helper.task_alerted, 0);
        }
        if(task.isCompleted()) {
            cv.put(DB_Helper.task_completed, 1);
        }
        else {
            cv.put(DB_Helper.task_completed, 0);
        }
        if(task.isRepeat()) {
            cv.put(DB_Helper.task_repeat, 1);
        }
        else {
            cv.put(DB_Helper.task_repeat, 0);
        }
        cv.put(DB_Helper.task_type,task.getType());

        db.insert(DB_Helper.tasks_table,null,cv);
    }

//      getting tasks
    public FullTask getFullTask(int task_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s NATURAL JOIN %s WHERE %s=%s;",DB_Helper.tasks_table,DB_Helper.full_task_table,DB_Helper.task_id,task_id);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Date date=new Date(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_day)),cursor.getInt(cursor.getColumnIndex(DB_Helper.task_month)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_year)));

            Location location=new Location(cursor.getString(cursor.getColumnIndex(DB_Helper.task_location_name)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_latitude)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_longitude)));

            Time time=new Time(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_hour)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_minute)));
            Time alert_time=new Time(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alert_hour)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alert_minute)));


            FullTask fullTask=new FullTask(task_id,cursor.getString(cursor.getColumnIndex(DB_Helper.task_description)),
                    date,location,cursor.getFloat(cursor.getColumnIndex(DB_Helper.task_location_range)),
                    time,alert_time);

            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alerted))==0){
                fullTask.setAlerted(false);
            }
            else {
                fullTask.setAlerted(true);
            }
            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_completed))==0){
                fullTask.setCompleted(false);
            }
            else {
                fullTask.setCompleted(true);
            }
            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_repeat))==0){
                fullTask.setRepeat(false);
            }
            else {
                fullTask.setRepeat(true);
            }

            return fullTask;

        }
        return null;
    }

    public LocationTask getLocationTask(int task_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s NATURAL JOIN %s WHERE %s=%s;",DB_Helper.tasks_table,DB_Helper.location_task_table,DB_Helper.task_id,task_id);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Date date=new Date(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_day)),cursor.getInt(cursor.getColumnIndex(DB_Helper.task_month)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_year)));

            Location location=new Location(cursor.getString(cursor.getColumnIndex(DB_Helper.task_location_name)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_latitude)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_longitude)));


            LocationTask locationTask=new LocationTask(task_id,cursor.getString(cursor.getColumnIndex(DB_Helper.task_description)),
                    date,location,cursor.getFloat(cursor.getColumnIndex(DB_Helper.task_location_range)));

            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alerted))==0){
                locationTask.setAlerted(false);
            }
            else {
                locationTask.setAlerted(true);
            }
            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_completed))==0){
                locationTask.setCompleted(false);
            }
            else {
                locationTask.setCompleted(true);
            }
            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_repeat))==0){
                locationTask.setRepeat(false);
            }
            else {
                locationTask.setRepeat(true);
            }

            return locationTask;

        }
        return null;
    }

    public TimeTask getTimeTask(int task_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s NATURAL JOIN %s WHERE %s=%s;",DB_Helper.tasks_table,DB_Helper.full_task_table,DB_Helper.task_id,task_id);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Date date=new Date(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_day)),cursor.getInt(cursor.getColumnIndex(DB_Helper.task_month)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_year)));

            Location location=new Location(cursor.getString(cursor.getColumnIndex(DB_Helper.task_location_name)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_latitude)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_longitude)));

            Time time=new Time(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_hour)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_minute)));
            Time alert_time=new Time(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alert_hour)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alert_minute)));


            TimeTask timeTask=new TimeTask(task_id,cursor.getString(cursor.getColumnIndex(DB_Helper.task_description)),
                    date,time,alert_time);

            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alerted))==0){
                timeTask.setAlerted(false);
            }
            else {
                timeTask.setAlerted(true);
            }
            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_completed))==0){
                timeTask.setCompleted(false);
            }
            else {
                timeTask.setCompleted(true);
            }
            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_repeat))==0){
                timeTask.setRepeat(false);
            }
            else {
                timeTask.setRepeat(true);
            }

            return timeTask;

        }
        return null;
    }

    public ArrayList<Task> getAllTasks(Date date){
        ArrayList<Task> tasks=new ArrayList<>();

        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT %s,%s FROM %s WHERE %s=%s AND %s=%s AND %s=%s;",
                DB_Helper.task_id,DB_Helper.task_type,DB_Helper.tasks_table,
                DB_Helper.task_year,date.getYear(),DB_Helper.task_month,date.getMonth(),DB_Helper.task_day,date.getDay());

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals("FULL")){
                tasks.add(getFullTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals("LOCATION")){
                tasks.add(getLocationTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals("TIME")){
                tasks.add(getTimeTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
        }

        return tasks;
    }

//      setting task states
    public void setTaskAlerted(int task_id,boolean alerted){
        SQLiteDatabase db=db_helper.getWritableDatabase();
        if(alerted) {
            db.execSQL(String.format("UPDATE %s SET %s=%s WHERE %s=%s;",
                    DB_Helper.tasks_table,DB_Helper.task_alerted,1,DB_Helper.task_id,task_id));
        }
        else{
            db.execSQL(String.format("UPDATE %s SET %s=%s WHERE %s=%s;",
                    DB_Helper.tasks_table,DB_Helper.task_alerted,0,DB_Helper.task_id,task_id));
        }
    }

    public void setTaskCompleted(int task_id,boolean completed){
        SQLiteDatabase db=db_helper.getWritableDatabase();
        if(completed) {
            db.execSQL(String.format("UPDATE %s SET %s=%s WHERE %s=%s;",
                    DB_Helper.tasks_table,DB_Helper.task_completed,1,DB_Helper.task_id,task_id));
        }
        else{
            db.execSQL(String.format("UPDATE %s SET %s=%s WHERE %s=%s;",
                    DB_Helper.tasks_table,DB_Helper.task_completed,0,DB_Helper.task_id,task_id));
        }
    }

    public void setTaskRepeat(int task_id,boolean repeat){
        SQLiteDatabase db=db_helper.getWritableDatabase();
        if(repeat) {
            db.execSQL(String.format("UPDATE %s SET %s=%s WHERE %s=%s;",
                    DB_Helper.tasks_table,DB_Helper.task_repeat,1,DB_Helper.task_id,task_id));
        }
        else{
            db.execSQL(String.format("UPDATE %s SET %s=%s WHERE %s=%s;",
                    DB_Helper.tasks_table,DB_Helper.task_repeat,0,DB_Helper.task_id,task_id));
        }
    }

//      remove a task
    public void removeTask(int task_id){
        SQLiteDatabase db=db_helper.getWritableDatabase();
        db.delete(DB_Helper.tasks_table,DB_Helper.task_id+"="+task_id,null);
        db.delete(DB_Helper.full_task_table,DB_Helper.task_id+"="+task_id,null);
        db.delete(DB_Helper.time_task_table,DB_Helper.task_id+"="+task_id,null);
        db.delete(DB_Helper.location_task_table,DB_Helper.task_id+"="+task_id,null);
    }



}
