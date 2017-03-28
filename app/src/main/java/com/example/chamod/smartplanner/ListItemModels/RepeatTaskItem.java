package com.example.chamod.smartplanner.ListItemModels;

import com.example.chamod.smartplanner.Models.Tasks.Task;

/**
 * Created by chamod on 3/28/17.
 */

public class RepeatTaskItem {
    private Task task;
    private boolean isFirstItem=false;

    public RepeatTaskItem(Task task){
        this.task=task;
    }

    public Task getTask() {
        return task;
    }

    public boolean isFirstItem() {
        return isFirstItem;
    }

    public void setFirstItem(boolean firstItem) {
        isFirstItem = firstItem;
    }
}
