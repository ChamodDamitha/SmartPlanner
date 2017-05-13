package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.chamod.smartplanner.Constants;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Message;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.MessageTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.Models.TimeSet;

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

    private TimesetDB timesetDB;
    private LocationDB locationDB;
    private MessageDB messageDB;

    private TaskDB(Context context){

        db_helper=DB_Helper.getInstance(context);
        timesetDB=TimesetDB.getInstance(context);
        locationDB=LocationDB.getInstance(context);
        messageDB=MessageDB.getInstance(context);
    }


//    public int getNextTaskId(){
//        SQLiteDatabase db=db_helper.getReadableDatabase();
//        String query = String.format("SELECT MAX(%s) as max_id FROM %s ;",DB_Helper.task_id,DB_Helper.tasks_table);
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToNext())
//        {
//            return cursor.getInt(cursor.getColumnIndex("max_id"))+1;
//        }
////        when no record in table
//        return 1;
//    }


//    adding tasks
    public boolean addFullTask(FullTask fullTask){
        timesetDB.addTimeRecord(fullTask.getTimeSet());
        locationDB.addLocationRecord(fullTask.getLocation());
        addTask(fullTask);
        return true;
    }

    public boolean addLocationTask(LocationTask locationTask) {
        locationDB.addLocationRecord(locationTask.getLocation());
        addTask(locationTask);
        return true;
    }

    public boolean addTimeTask(TimeTask timeTask){
        timesetDB.addTimeRecord(timeTask.getTimeSet());
        addTask(timeTask);
        return true;
    }
    public boolean addMessageTask(MessageTask messageTask){
        messageDB.addMessageRecord(messageTask.getMessage());
        addTask(messageTask);
        return true;
    }

    private boolean addTask(Task task){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_description,task.getDescription());
        cv.put(DB_Helper.task_date,task.getDate().toString());

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


        if(task.getType().equals(Constants.TIME_TYPE)){
            cv.put(DB_Helper.timeset_id,((TimeTask)task).getTimeSet().getTimeset_id());
        }
        else if(task.getType().equals(Constants.LOCATION_TYPE)){
            cv.put(DB_Helper.loc_id,((LocationTask)task).getLocation().getLoc_id());
        }
        else if(task.getType().equals(Constants.FULL_TYPE)){
            FullTask fullTask=(FullTask)task;
            cv.put(DB_Helper.loc_id,fullTask.getLocation().getLoc_id());
            cv.put(DB_Helper.timeset_id,fullTask.getTimeSet().getTimeset_id());
        }
        else if(task.getType().equals(Constants.MESSAGE_TYPE)){
            cv.put(DB_Helper.msg_id,((MessageTask)task).getMessage().getMsg_id());
        }

        db.insert(DB_Helper.tasks_table,null,cv);

//      set task_id
        db=db_helper.getReadableDatabase();
        String query = String.format("SELECT MAX(%s) as max_id FROM %s ;",DB_Helper.task_id,DB_Helper.tasks_table);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            task.setId(cursor.getInt(cursor.getColumnIndex("max_id")));
            return true;
        }
        return false;
    }

//    updating tasks
    public boolean updateFullTask(FullTask fullTask){
        timesetDB.updateTimesetRecord(fullTask.getTimeSet());
        locationDB.updateLocationRecord(fullTask.getLocation());
        updateTask(fullTask);
        return true;
    }

    public boolean updateLocationTask(LocationTask locationTask) {
        locationDB.updateLocationRecord(locationTask.getLocation());
        updateTask(locationTask);
        return true;
    }

    public boolean updateTimeTask(TimeTask timeTask){
        timesetDB.updateTimesetRecord(timeTask.getTimeSet());
        updateTask(timeTask);
        return true;
    }
    public boolean updateMessageTask(MessageTask messageTask){
        messageDB.updateMessageRecord(messageTask.getMessage());
        updateTask(messageTask);
        return true;
    }

    public boolean updateTask(Task task){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.task_description,task.getDescription());
        cv.put(DB_Helper.task_date,task.getDate().toString());

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


        if(task.getType().equals(Constants.TIME_TYPE)){
            cv.put(DB_Helper.timeset_id,((TimeTask)task).getTimeSet().getTimeset_id());
        }
        else if(task.getType().equals(Constants.LOCATION_TYPE)){
            cv.put(DB_Helper.loc_id,((LocationTask)task).getLocation().getLoc_id());
        }
        else if(task.getType().equals(Constants.FULL_TYPE)){
            FullTask fullTask=(FullTask)task;
            cv.put(DB_Helper.loc_id,fullTask.getLocation().getLoc_id());
            cv.put(DB_Helper.timeset_id,fullTask.getTimeSet().getTimeset_id());
        }
        else if(task.getType().equals(Constants.MESSAGE_TYPE)){
            cv.put(DB_Helper.msg_id,((MessageTask)task).getMessage().getMsg_id());
        }

        db.update(DB_Helper.tasks_table,cv,DB_Helper.task_id+"="+task.getId(),null);

        return true;
    }


//      getting tasks

    public FullTask getFullTask(int task_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s=%s AND %s='%s';",
                DB_Helper.tasks_table, DB_Helper.task_id,task_id,DB_Helper.task_type, Constants.FULL_TYPE);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Date date=new Date(cursor.getString(cursor.getColumnIndex(DB_Helper.task_date)));

            Location location=locationDB.getLocationRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.loc_id)));

            TimeSet timeSet=timesetDB.getTimesetRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.timeset_id)));


            FullTask fullTask=new FullTask(cursor.getString(cursor.getColumnIndex(DB_Helper.task_description)),
                    date,location,timeSet);
            fullTask.setId(task_id);

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
        String query = String.format("SELECT * FROM %s WHERE %s=%s AND %s='%s';",
                DB_Helper.tasks_table, DB_Helper.task_id,task_id,DB_Helper.task_type, Constants.LOCATION_TYPE);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Date date=new Date(cursor.getString(cursor.getColumnIndex(DB_Helper.task_date)));

            Location location=locationDB.getLocationRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.loc_id)));


            LocationTask locationTask=new LocationTask(cursor.getString(cursor.getColumnIndex(DB_Helper.task_description)),
                    date,location);
            locationTask.setId(task_id);

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
        String query = String.format("SELECT * FROM %s WHERE %s=%s AND %s='%s';",
                DB_Helper.tasks_table, DB_Helper.task_id,task_id,DB_Helper.task_type, Constants.TIME_TYPE);

        Cursor cursor = db.rawQuery(query, null);



        if (cursor.moveToNext())
        {

            Date date=new Date(cursor.getString(cursor.getColumnIndex(DB_Helper.task_date)));

            TimeSet timeSet=timesetDB.getTimesetRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.timeset_id)));



            Log.e("xxx",timeSet.getTask_time().toString());


            TimeTask timeTask=new TimeTask(cursor.getString(cursor.getColumnIndex(DB_Helper.task_description)),
                    date,timeSet);
            timeTask.setId(task_id);

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


    public MessageTask getMessageTask(int task_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s=%s AND %s='%s';",
                DB_Helper.tasks_table, DB_Helper.task_id,task_id,DB_Helper.task_type, Constants.MESSAGE_TYPE);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Date date=new Date(cursor.getString(cursor.getColumnIndex(DB_Helper.task_date)));

            Message message=messageDB.getMessageRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.msg_id)));

            TimeSet timeSet=timesetDB.getTimesetRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.timeset_id)));



            MessageTask messageTask=new MessageTask(message,date,timeSet.getTask_time());

            messageTask.setId(task_id);

            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_alerted))==0){
                messageTask.setAlerted(false);
            }
            else {
                messageTask.setAlerted(true);
            }
            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_completed))==0){
                messageTask.setCompleted(false);
            }
            else {
                messageTask.setCompleted(true);
            }
            if(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_repeat))==0){
                messageTask.setRepeat(false);
            }
            else {
                messageTask.setRepeat(true);
            }

            return messageTask;

        }
        return null;
    }

    public ArrayList<Task> getAllTasks(Date date){
        ArrayList<Task> tasks=new ArrayList<>();

        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT %s,%s FROM %s WHERE %s='%s' ;",
                DB_Helper.task_id,DB_Helper.task_type,DB_Helper.tasks_table,
                DB_Helper.task_date,date.toString());

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.FULL_TYPE)){
                tasks.add(getFullTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.LOCATION_TYPE)){
                tasks.add(getLocationTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.TIME_TYPE)){
                tasks.add(getTimeTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.MESSAGE_TYPE)){
                tasks.add(getMessageTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
        }

        return tasks;
    }
//    get all repeating tasks
    public  ArrayList<Task> getAllRepeatingTasks(){
        ArrayList<Task> tasks=new ArrayList<>();

        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT %s,%s FROM %s WHERE %s=%s ;",
                DB_Helper.task_id,DB_Helper.task_type,DB_Helper.tasks_table,
                DB_Helper.task_repeat,1);

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.FULL_TYPE)){
                tasks.add(getFullTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.LOCATION_TYPE)){
                tasks.add(getLocationTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.TIME_TYPE)){
                tasks.add(getTimeTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.MESSAGE_TYPE)){
                tasks.add(getMessageTask(cursor.getInt(cursor.getColumnIndex(DB_Helper.task_id))));
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
    public boolean removeTask(int task_id){

        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT %s,%s,%s,%s FROM %s WHERE %s=%s ;",
                DB_Helper.task_type,DB_Helper.timeset_id,DB_Helper.loc_id,DB_Helper.msg_id,
                DB_Helper.tasks_table,DB_Helper.task_id,task_id);

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.FULL_TYPE)){
                timesetDB.deleteTimeRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.timeset_id)));
                locationDB.deleteLocationRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.loc_id)));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.LOCATION_TYPE)){
                locationDB.deleteLocationRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.loc_id)));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.TIME_TYPE)){
                timesetDB.deleteTimeRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.timeset_id)));
            }
            else if(cursor.getString(cursor.getColumnIndex(DB_Helper.task_type)).equals(Constants.MESSAGE_TYPE)){
                timesetDB.deleteTimeRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.timeset_id)));
                messageDB.deleteMessageRecord(cursor.getInt(cursor.getColumnIndex(DB_Helper.msg_id)));
            }
        }

        db=db_helper.getWritableDatabase();
        db.delete(DB_Helper.tasks_table,DB_Helper.task_id+"="+task_id,null);
        return true;
    }



}
