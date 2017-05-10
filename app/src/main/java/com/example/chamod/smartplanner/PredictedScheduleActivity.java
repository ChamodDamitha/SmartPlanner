package com.example.chamod.smartplanner;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chamod.smartplanner.EventHandlers.PredictedListListener;
import com.example.chamod.smartplanner.EventHandlers.PredictedTasksListener;
import com.example.chamod.smartplanner.Fragments.DateFragment;
import com.example.chamod.smartplanner.Handlers.ScheduleHandler;
import com.example.chamod.smartplanner.ListItemModels.PredictedTaskListAdapter;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class PredictedScheduleActivity extends AppCompatActivity implements DateFragment.DateFragmentListener,PredictedTasksListener{
    Date selected_date=null;
    TextView dateTextView;

    ScheduleHandler scheduleHandler;


    PredictedListListener predictedListListener;

    LinearLayout predictedListPad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predicted_schedule);

        predictedListPad=(LinearLayout)findViewById(R.id.predictedListPad);

        scheduleHandler=ScheduleHandler.getInstance(this);
        scheduleHandler.addPredictedTaskListener(this);


        dateTextView=(TextView)findViewById(R.id.textViewDate);

        //        set today
        Calendar calendar=Calendar.getInstance();
        selected_date = new Date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));

        dateTextView.setText(selected_date.getDateString());

        CheckBox checkBoxSelectAllTasks=(CheckBox)findViewById(R.id.checkBoxSelectAllTaks);
        checkBoxSelectAllTasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                predictedListListener.selectTask(isChecked);
            }
        });
    }


    public void requestSchedule(View v){
        scheduleHandler.requestPredictedTasks(selected_date);
    }

    public void showDatePicker(View view){
        FragmentManager fm=getSupportFragmentManager();
        DateFragment dateFragment=new DateFragment();
        dateFragment.show(fm,"DateFragment");
    }

    @Override
    public void setDate(int year, int month, int day) {
        selected_date=new Date(day,month,year);
        dateTextView.setText(selected_date.getDateString());
        scheduleHandler.requestPredictedTasks(selected_date);
    }

    @Override
    public void predictedTasksReceived(ArrayList<Task> tasks) {
        predictedListPad.setVisibility(View.VISIBLE);
        //set list view
        ListView predictedListView=(ListView)findViewById(R.id.predictedTasksListView);
        ArrayAdapter predictedTaskListAdapter=new PredictedTaskListAdapter(this,tasks);

        predictedListListener=(PredictedListListener)predictedTaskListAdapter;

        predictedListView.setAdapter(predictedTaskListAdapter);
    }

    @Override
    public void notifyNoSchedule() {
        predictedListPad.setVisibility(View.GONE);
    }

    public void savePredictedTasks(View view){
        predictedListListener.acceptTasks();
        startActivity(new Intent(this,NavigaterActivity.class));
    }

    public void CancelPredictedSchedule(View view){
        startActivity(new Intent(this,NavigaterActivity.class));
    }

}
