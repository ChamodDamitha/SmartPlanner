package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamod.smartplanner.Constants;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.MessageTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.NewTaskActivity;
import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 2/12/17.
 */

public class TaskListAdapter extends ArrayAdapter<Task> {

    TaskHandler taskHandler;
    Context context;

    public TaskListAdapter(Context context, Task[] items) {
        super(context, R.layout.task_list_item,items);
        taskHandler=TaskHandler.getInstance(context);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        final View customView = layoutInflater.inflate(R.layout.task_list_item,parent,false);

        ImageButton btnMore=(ImageButton) customView.findViewById(R.id.btnMore);

        TextView textViewTime=(TextView)customView.findViewById(R.id.textViewTime);
        TextView textViewDescription=(TextView)customView.findViewById(R.id.textViewDescription);
        TextView textViewLocation=(TextView)customView.findViewById(R.id.textViewLocation);

        FloatingActionButton btnDelTask = (FloatingActionButton)customView.findViewById(R.id.btnDelTask);

        ImageView taskStateView=(ImageView)customView.findViewById(R.id.taskStateView);

        final Task task=getItem(position);
        textViewDescription.setText(task.getDescription());

        if(task.getType().equals(Constants.TIME_TYPE)){
            textViewTime.setText(((TimeTask)task).getTimeSet().getTask_time().getTimeString());
            textViewLocation.setText(null);
        }
        else if(task.getType().equals(Constants.LOCATION_TYPE)){
            textViewLocation.setText("At " + ((LocationTask)task).getLocation().getName());
            textViewTime.setText(null);
        }
        else if(task.getType().equals(Constants.FULL_TYPE)){
            FullTask fullTask=(FullTask)task;
            textViewTime.setText(fullTask.getTimeSet().getTask_time().getTimeString());
            textViewLocation.setText("At " + fullTask.getLocation().getName());
        }
        else if(task.getType().equals(Constants.MESSAGE_TYPE)){
            MessageTask messageTask=(MessageTask) task;
            textViewTime.setText(messageTask.getTimeSet().getTask_time().getTimeString());
            textViewLocation.setText("To " + messageTask.getMessage().getReceiver().getName());
        }

        if(task.isAlerted()){
            btnDelTask.setVisibility(View.GONE);
           if(task.isCompleted()){
                taskStateView.setImageDrawable(customView.getResources().getDrawable(R.drawable.tick_green));
           }
           else{
               taskStateView.setImageDrawable(customView.getResources().getDrawable(R.drawable.cross));
           }
        }

//      More button functionality
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.widget.PopupMenu popupMenu=new android.widget.PopupMenu(customView.getContext(),customView);
                popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        switch (id){
                            case R.id.action_edit_task:
                                Intent i=new Intent(context, NewTaskActivity.class);
                                i.putExtra("task_id",task.getId());
                                i.putExtra("task_type",task.getType());

                                context.startActivity(i);
                                break;
                            case R.id.action_repeat_task:
                                taskHandler.setRepeatTask(task.getId(),true);
                                Toast.makeText(customView.getContext(),"Task scheduled for every week",Toast.LENGTH_LONG).show();
                                break;
                        }

                        return true;
                    }
                });

                if(task.isAlerted()){
                    popupMenu.inflate(R.menu.alerted_task_popup_menu);
                }
                else {
                    popupMenu.inflate(R.menu.task_item_popup_menu);
                }
                popupMenu.show();

            }
        });


//      delete button functionality
        btnDelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Alert")
                        .setMessage("Are you sure you want to remove the task ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                taskHandler.removeTask(task);
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

        return customView;
    }
}
