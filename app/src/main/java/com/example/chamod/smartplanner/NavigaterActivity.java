package com.example.chamod.smartplanner;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GestureDetectorCompat;
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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamod.smartplanner.EventHandlers.TaskEvent;
import com.example.chamod.smartplanner.EventHandlers.TaskEventListner;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.ListItemModels.TaskListAdapter;
import com.example.chamod.smartplanner.Models.Date;

import java.util.Calendar;

public class NavigaterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,TaskEventListner{

    private GestureDetectorCompat mDetector;


    CalendarView calendarView;
    ListView taskListView;
    ArrayAdapter taskListAdapter;

    TextView txtViewDate;

    TaskHandler taskHandler;

    LinearLayout floatingBtnPad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigater);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//      Start listening to task change event
        TaskEvent.getInstance().addNewTaskEventListner(this);


        floatingBtnPad=(LinearLayout)findViewById(R.id.floatingBtnPad);

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


        taskHandler=TaskHandler.getInstance(this);
//
        txtViewDate=(TextView)findViewById(R.id.txtViewDate);

        calendarView=(CalendarView)findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Date selected_date=new Date(dayOfMonth,month+1,year);
                calendar_date_set(selected_date);

            }
        });

        taskListView=(ListView)findViewById(R.id.taskListView);


//.....................set initial date on calendar............................................

            //        initial date set up
            java.util.Date date=new java.util.Date(calendarView.getDate());
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);

            Date today_date = new Date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.YEAR));
            calendar_date_set(today_date);


//      ................Permission request.....................................................
        requestPermissions();
    }

    private void requestPermissions(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

            return;
        }
    }

    //    ...............................Permission response..........................................

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Constants.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }
                else{
                    requestPermissions();
                }
            }
        }
    }







    private void calendar_date_set(Date date){
        //set list view
        taskListAdapter=new TaskListAdapter(NavigaterActivity.this,taskHandler.getAllTasks(date));
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

//    ...........................................Custom event handling.................................
//    handle task change event
    @Override
    public void taskChanged() {

        java.util.Date date=new java.util.Date(calendarView.getDate());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        Date today_date = new Date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
        calendar_date_set(today_date);
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
                    fadeInOutAnimation(floatingBtnPad,false);
                    return true;
                case 2:
//                    Toast.makeText(NavigaterActivity.this,"left",Toast.LENGTH_LONG).show();
                    return true;
                case 3:
//                    Toast.makeText(NavigaterActivity.this,"down",Toast.LENGTH_LONG).show();
                    calendarView.setVisibility(View.VISIBLE);
                    fadeInOutAnimation(floatingBtnPad,true);
                    return true;
                case 4:
//                    Toast.makeText(NavigaterActivity.this,"right",Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }

        private void fadeInOutAnimation(final View view, final boolean isFadeIn){
            ObjectAnimator objectAnimator;
            if(isFadeIn) {
                objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            }
            else{
                objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            }

            objectAnimator.setDuration(1000);
            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(objectAnimator);

            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if(isFadeIn){
                        view.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(!isFadeIn){
                        view.setVisibility(View.GONE);
                    }
                    else{
                        view.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animatorSet.start();
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
