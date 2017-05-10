package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.chamod.smartplanner.EventHandlers.PredictedListListener;
import com.example.chamod.smartplanner.EventHandlers.PredictedTasksListener;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.R;

import java.util.ArrayList;

/**
 * Created by chamod on 2/11/17.
 */

public class PredictedTaskListAdapter extends ArrayAdapter<Task> implements PredictedListListener{

    public PredictedTaskListAdapter(Context context, ArrayList<Task> items) {
        super(context, R.layout.predicted_list_item,items);
        taskHandler=TaskHandler.getInstance(context);
    }

    ArrayList<CheckBox> checkBoxes=new ArrayList<>();
    ArrayList<Task> tasks=new ArrayList<>();

    TaskHandler taskHandler;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View customView=layoutInflater.inflate(R.layout.predicted_list_item,parent,false);
        TextView textViewTime=(TextView)customView.findViewById(R.id.textViewTime);
        TextView textViewDescription=(TextView)customView.findViewById(R.id.textViewDescription);
        TextView textViewLocation=(TextView)customView.findViewById(R.id.textViewLocation);

        CheckBox checkBox=(CheckBox)customView.findViewById(R.id.checkBoxTask);
        checkBoxes.add(checkBox);

        final Task task=getItem(position);
        textViewDescription.setText(task.getDescription());

        if(task.getType().equals("TIME")){
            textViewTime.setText(((TimeTask)task).getTimeSet().getTask_time().getTimeString());
            textViewLocation.setText(null);
            textViewLocation.setVisibility(View.GONE);
        }
        else if(task.getType().equals("LOCATION")){
            textViewLocation.setText("At " + ((LocationTask)task).getLocation().getName());
            textViewTime.setText(null);
            textViewTime.setVisibility(View.GONE);
        }
        else {
            FullTask fullTask=(FullTask)task;
            textViewTime.setText(fullTask.getTimeSet().getTask_time().getTimeString());
            textViewLocation.setText("At " + fullTask.getLocation().getName());
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && tasks.indexOf(task)==-1) {
                    tasks.add(task);
                }
                else if(!isChecked && tasks.indexOf(task)!=-1){
                    tasks.remove(task);
                }
            }
        });


        return customView;
    }

    @Override
    public void acceptTasks() {
        for(Task task:tasks){
            taskHandler.saveNewTask(task);
        }
    }

    @Override
    public void selectTask(boolean selected) {
        for(CheckBox checkBox:checkBoxes) {
            checkBox.setChecked(selected);
        }
    }
}
