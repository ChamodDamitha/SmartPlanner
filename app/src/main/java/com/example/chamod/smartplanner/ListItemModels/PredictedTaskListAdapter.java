package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.R;

import java.util.ArrayList;

/**
 * Created by chamod on 2/11/17.
 */

public class PredictedTaskListAdapter extends ArrayAdapter<Task> {

    public PredictedTaskListAdapter(Context context, ArrayList<Task> items) {
        super(context, R.layout.predicted_list_item,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View customView=layoutInflater.inflate(R.layout.predicted_list_item,parent,false);
        TextView textViewTime=(TextView)customView.findViewById(R.id.textViewTime);
        TextView textViewDescription=(TextView)customView.findViewById(R.id.textViewDescription);
        TextView textViewLocation=(TextView)customView.findViewById(R.id.textViewLocation);


        Task task=getItem(position);
        textViewDescription.setText(task.getDescription());

        if(task.getType().equals("TIME")){
            textViewTime.setText(((TimeTask)task).getTimeSet().getTask_time().getTimeString());
            textViewLocation.setText(null);
        }
        else if(task.getType().equals("LOCATION")){
            textViewLocation.setText("At " + ((LocationTask)task).getLocation().getName());
            textViewTime.setText(null);
        }
        else {
            FullTask fullTask=(FullTask)task;
            textViewTime.setText(fullTask.getTimeSet().getTask_time().getTimeString());
            textViewLocation.setText("At " + fullTask.getLocation().getName());
        }

        return customView;
    }
}
