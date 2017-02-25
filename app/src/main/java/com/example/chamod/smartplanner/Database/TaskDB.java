package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Task;
import com.example.chamod.smartplanner.Models.Time;

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
        String query = String.format("SELECT MAX(%s) as max_id FROM %s ;",DB_Helper.task_id,DB_Helper.task_table);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            return cursor.getInt(cursor.getColumnIndex("max_id"))+1;
        }
//        when no record in table
        return 1;
    }

    public void addFullTask(FullTask fullTask){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_id,fullTask.getId());
        cv.put(DB_Helper.task_description,fullTask.getDescription());
        cv.put(DB_Helper.task_year,fullTask.getDate().getYear());
        cv.put(DB_Helper.task_month,fullTask.getDate().getMonth());
        cv.put(DB_Helper.task_day,fullTask.getDate().getDay());

        if(fullTask.isAlerted()) {
            cv.put(DB_Helper.task_alerted, 1);
        }
        else {
            cv.put(DB_Helper.task_alerted, 0);
        }
        if(fullTask.isCompleted()) {
            cv.put(DB_Helper.task_completed, 1);
        }
        else {
            cv.put(DB_Helper.task_completed, 0);
        }
        cv.put(DB_Helper.task_type,fullTask.getType());


        ContentValues cv2=new ContentValues();
        cv2.put(DB_Helper.task_id,fullTask.getId());

        cv2.put(DB_Helper.task_hour,fullTask.getTime().get24Hour());
        cv2.put(DB_Helper.task_minute,fullTask.getTime().getMinute());
        cv2.put(DB_Helper.task_alert_hour,fullTask.getAlert_time().get24Hour());
        cv2.put(DB_Helper.task_hour,fullTask.getAlert_time().getMinute());

        cv2.put(DB_Helper.task_location_name,fullTask.getLocation().getName());
        cv2.put(DB_Helper.task_location_latitude,fullTask.getLocation().getLatitude());
        cv2.put(DB_Helper.task_location_longitude,fullTask.getLocation().getLongitude());
        cv2.put(DB_Helper.task_location_range,fullTask.getRange());


        db.insert(DB_Helper.task_table,null,cv);
        db.insert(DB_Helper.full_task_table,null,cv2);
    }


    public FullTask getFullTask(int task_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s NATURAL JOIN %s WHERE %s=%s;",DB_Helper.task_table,DB_Helper.full_task_table,DB_Helper.task_id,task_id);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Date date=new Date(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_day)),cursor.getInt(cursor.getColumnIndex(DB_Helper.task_month)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_year)),cursor.getString(cursor.getColumnIndex(DB_Helper.task_day_of_week)));

            Location location=new Location(cursor.getString(cursor.getColumnIndex(DB_Helper.task_location_name)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_latitude)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_longitude)));

            Time time=new Time(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_hour)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_minute)));
            Time alert_time=new Time(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alert_hour)),
                    cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alert_minute)));


            FullTask fullTask=new FullTask(task_id,cursor.getString(cursor.getColumnIndex(DB_Helper.task_description)),
                    date,location,cursor.getDouble(cursor.getColumnIndex(DB_Helper.task_location_range)),
                    time,alert_time);

            return fullTask;

        }
        return null;
    }
}
