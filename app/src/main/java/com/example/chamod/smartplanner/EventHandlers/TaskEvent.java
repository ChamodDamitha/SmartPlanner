package com.example.chamod.smartplanner.EventHandlers;

import java.util.ArrayList;

/**
 * Created by chamod on 3/23/17.
 */

public class TaskEvent {
    private static TaskEvent taskEvent=null;

    public static TaskEvent getInstance(){
        if(taskEvent==null){
            taskEvent=new TaskEvent();
        }
        return taskEvent;
    }

    private TaskEvent(){

    }

    private ArrayList<TaskEventListner> taskEventListners=new ArrayList<>();

    public void addNewTaskEventListner(TaskEventListner taskEventListner){
        taskEventListners.add(taskEventListner);
    }

    public void taskChangedEventOccured(){
        for(TaskEventListner taskEventListner:taskEventListners){
            taskEventListner.taskChanged();
        }
    }
}
