package com.example.chamod.smartplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.ListItemModels.TaskItem;
import com.example.chamod.smartplanner.ListItemModels.TaskListAdapter;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    TaskDB taskDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskDB=TaskDB.getInstance(this);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        calendarView=(CalendarView)findViewById(R.id.calendarView);



        //set list view
        ArrayList<Task> scheduledTasks=taskDB.getAllScheduledTasks();

        TaskItem[] tasks=new TaskItem[scheduledTasks.size()];
        for (int i=0;i<tasks.length;i++){
            Task task=scheduledTasks.get(i);
            if(task.getType().equals("FULL")) {
                FullTask fulltask=(FullTask)task;
                tasks[i] = new TaskItem(fulltask.getDescription(), fulltask.getTime().get24Hour()+":"+fulltask.getTime().getMinute()+" "+fulltask.getTime().getAmOrPM() ,
                        fulltask.getLocation().getName());
            }
        }



        ListView taskListView=(ListView)findViewById(R.id.taskListView);
        ArrayAdapter taskListAdapter=new TaskListAdapter(this,tasks);
        taskListView.setAdapter(taskListAdapter);

    }

    public void hideCalendar(View v){
        if(calendarView.getVisibility()==View.GONE){
            calendarView.setVisibility(View.VISIBLE);
        }
        else {
            calendarView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(this,SettingsActivity.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.action_my_places) {
            Intent i=new Intent(this,MyPlacesActivity.class);
            startActivity(i);
            return true;
        }else if (id == R.id.action_repeating_tasks) {
            Intent i=new Intent(this,RepeatingTasksActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newTaskButtonClicked(View v){
        Intent i=new Intent(this,NewTaskActivity.class);
        startActivity(i);
    }

    public void predictTasksButtonClicked(View v) {
        Intent i = new Intent(this, PredictedScheduleActivity.class);
        startActivity(i);
    }
    public void reportsButtonClicked(View v){
        Intent i=new Intent(this,ReportsActivity.class);
        startActivity(i);
    }



}
