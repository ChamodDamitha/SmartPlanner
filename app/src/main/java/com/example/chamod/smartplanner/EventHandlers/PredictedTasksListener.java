package com.example.chamod.smartplanner.EventHandlers;

import com.example.chamod.smartplanner.Models.Tasks.Task;

import java.util.ArrayList;

/**
 * Created by chamod on 5/10/17.
 */

public interface PredictedTasksListener {
    void predictedTasksReceived(ArrayList<Task> tasks);
    void notifyNoSchedule();
}
