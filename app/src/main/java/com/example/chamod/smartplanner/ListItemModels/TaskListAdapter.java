package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 2/12/17.
 */

public class TaskListAdapter extends ArrayAdapter<TaskItem> {

    public TaskListAdapter(Context context, TaskItem[] items) {
        super(context, R.layout.task_list_item,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.task_list_item,parent,false);

        TextView textViewTime=(TextView)customView.findViewById(R.id.textViewTime);
        TextView textViewDescription=(TextView)customView.findViewById(R.id.textViewDescription);
        TextView textViewLocation=(TextView)customView.findViewById(R.id.textViewLocation);

        TaskItem item=getItem(position);
        textViewTime.setText(item.getTime());

        textViewDescription.setText(item.getDescription());

        if(item.getLocation()!=null) {
            textViewLocation.setText("At " + item.getLocation());
        }
        else{
            textViewLocation.setText(null);
        }
        return customView;
    }
}
