package com.example.chamod.smartplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.EventHandlers.RepeatTaskChangeEvent;
import com.example.chamod.smartplanner.EventHandlers.RepeatTaskChangeEventListner;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.ListItemModels.RepeatTaskListAdapter;
import com.example.chamod.smartplanner.ListItemModels.TaskListAdapter;
import com.example.chamod.smartplanner.Models.Tasks.Task;

public class RepeatingTasksActivity extends AppCompatActivity implements RepeatTaskChangeEventListner{
    TaskHandler taskHandler;

    ListView listViewRepeatingTasks;
    RepeatTaskListAdapter repeatTaskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeating_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        RepeatTaskChangeEvent.getInstance().addNewRepeatTaskChangeEventListner(this);

        //set list view
        setRepeatTaskList();
    }

    private void setRepeatTaskList(){
        taskHandler=TaskHandler.getInstance(this);

        listViewRepeatingTasks=(ListView)findViewById(R.id.ListViewRepeatingTasks);
        repeatTaskListAdapter=new RepeatTaskListAdapter(this,taskHandler.getAllRepeatTasks());
        listViewRepeatingTasks.setAdapter(repeatTaskListAdapter);
    }


    @Override
    public void repeatTaskChanged() {
        setRepeatTaskList();
    }
}
