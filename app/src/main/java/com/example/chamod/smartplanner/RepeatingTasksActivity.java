package com.example.chamod.smartplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chamod.smartplanner.ListItemModels.TaskItem;
import com.example.chamod.smartplanner.ListItemModels.TaskListAdapter;

public class RepeatingTasksActivity extends AppCompatActivity {

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

        //set list view
        TaskItem[] tasks=new TaskItem[3];
        tasks[0]=new TaskItem("Meeting x","9.00 AM","Matara");
        tasks[1]=new TaskItem("Meeting y","1.00 PM","Colombo");
        tasks[2]=new TaskItem("Meeting z","3.00 PM","Galle");

        ListView listViewRepeatingTasks=(ListView)findViewById(R.id.ListViewRepeatingTasks);
        ArrayAdapter taskListAdapter=new TaskListAdapter(this,tasks);
        listViewRepeatingTasks.setAdapter(taskListAdapter);
    }

}
