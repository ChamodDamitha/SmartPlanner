package com.example.chamod.smartplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.ListItemModels.TaskItem;
import com.example.chamod.smartplanner.ListItemModels.TaskListAdapter;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Task;
import com.example.chamod.smartplanner.Models.TimeTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NavigaterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private GestureDetectorCompat mDetector;


    CalendarView calendarView;
    ListView taskListView;
    ArrayAdapter taskListAdapter;

    TextView txtViewDate;

    TaskDB taskDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigater);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//........................................................................................................................................................

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());


        taskDB=TaskDB.getInstance(this);
//
        txtViewDate=(TextView)findViewById(R.id.txtViewDate);

        final String[] days={"MON","TUE","WED","THU","FRI","SAT","SUN"};

        calendarView=(CalendarView)findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Date selected_date=new Date(dayOfMonth,month+1,year);
                calendar_date_set(selected_date);

            }
        });

        taskListView=(ListView)findViewById(R.id.taskListView);

//        initial date set up
        java.util.Date date=new java.util.Date(calendarView.getDate());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        Date today_date=new Date(calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.YEAR));
        calendar_date_set(today_date);
    }

    private void calendar_date_set(Date date){
        //set list view
        ArrayList<Task> tasks=taskDB.getAllScheduledTasks(date);

        TaskItem[] scheduled_tasks=new TaskItem[tasks.size()];
        for (int i=0;i<scheduled_tasks.length;i++){
            Task task=tasks.get(i);
            if(task.getType().equals("FULL")) {

                FullTask fulltask=(FullTask)task;


                scheduled_tasks[i] = new TaskItem(fulltask.getDescription(), fulltask.getTime().getTimeString() ,
                        fulltask.getLocation().getName());
            }
            else if(task.getType().equals("TIME")){
                TimeTask timeTask=(TimeTask)task;

                scheduled_tasks[i] = new TaskItem(timeTask.getDescription(), timeTask.getTime().getTimeString() ,
                        null);
            }
        }


        taskListAdapter=new TaskListAdapter(NavigaterActivity.this,scheduled_tasks);
        taskListView.setAdapter(taskListAdapter);

        txtViewDate.setText(date.getDateString());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigater, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    .........................Button action handling...............................................

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


//    ............................................Gesture handling.............................................


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);

        return this.mDetector.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {

            switch (getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY())) {
                case 1:
//                    Toast.makeText(NavigaterActivity.this,"top",Toast.LENGTH_LONG).show();
                    calendarView.setVisibility(View.GONE);

                    return true;
                case 2:
//                    Toast.makeText(NavigaterActivity.this,"left",Toast.LENGTH_LONG).show();
                    return true;
                case 3:
//                    Toast.makeText(NavigaterActivity.this,"down",Toast.LENGTH_LONG).show();
                    calendarView.setVisibility(View.VISIBLE);
                    return true;
                case 4:
//                    Toast.makeText(NavigaterActivity.this,"right",Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }

        private int getSlope(float x1, float y1, float x2, float y2) {
            Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));
            if (angle > 45 && angle <= 135)
                // top
                return 1;
            if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
                // left
                return 2;
            if (angle < -45 && angle>= -135)
                // down
                return 3;
            if (angle > -45 && angle <= 45)
                // right
                return 4;
            return 0;
        }
    }


}
