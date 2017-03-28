package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 3/27/17.
 */

public class RepeatTaskListAdapter extends ArrayAdapter<Task> {
    TaskHandler taskHandler;
    Context context;

    public RepeatTaskListAdapter(Context context, Task[] items) {
        super(context, R.layout.task_list_item,items);
        taskHandler=TaskHandler.getInstance(context);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View customView = layoutInflater.inflate(R.layout.task_list_item, parent, false);



        return customView;
    }
}
