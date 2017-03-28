package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chamod.smartplanner.EventHandlers.RepeatTaskChangeEvent;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 3/27/17.
 */

public class RepeatTaskListAdapter extends ArrayAdapter<RepeatTaskItem> {
    TaskHandler taskHandler;
    Context context;

    public RepeatTaskListAdapter(Context context, RepeatTaskItem[] items) {
        super(context, R.layout.repeat_task_list_item,items);
        taskHandler=TaskHandler.getInstance(context);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View customView = layoutInflater.inflate(R.layout.repeat_task_list_item, parent, false);

        TextView textViewDay=(TextView)customView.findViewById(R.id.textViewDay);
        TextView textViewDescription=(TextView)customView.findViewById(R.id.textViewDescription);
        TextView textViewTime=(TextView)customView.findViewById(R.id.textViewTime);
        TextView textViewLocation=(TextView)customView.findViewById(R.id.textViewLocation);


        FloatingActionButton btnCancelRepeat=(FloatingActionButton) customView.findViewById(R.id.btnCancelRepeat);

        RepeatTaskItem repeatTaskItem=getItem(position);
        final Task task=repeatTaskItem.getTask();

//        cancel repeat

        btnCancelRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Alert")
                        .setMessage("Are you sure you want to cancel the repeating task ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                taskHandler.setRepeatTask(task.getId(),false);
                                RepeatTaskChangeEvent.getInstance().repeatTaskChangedEventOccured();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


//        show task details
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


        if(repeatTaskItem.isFirstItem()){
            textViewDay.setVisibility(View.VISIBLE);
            textViewDay.setText(task.getDate().getFullDayOfWeek());
        }

        textViewDescription.setText(task.getDescription());





        return customView;
    }
}
