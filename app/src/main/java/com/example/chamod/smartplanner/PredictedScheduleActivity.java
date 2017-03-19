package com.example.chamod.smartplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chamod.smartplanner.ListItemModels.PredictedTaskListAdapter;
import com.example.chamod.smartplanner.Models.Task;

public class PredictedScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predicted_schedule);

        //set list view
        Task[] predictedTaskItems=new Task[0];

        ListView predictedListView=(ListView)findViewById(R.id.predictedTasksListView);
        ArrayAdapter predictedTaskListAdapter=new PredictedTaskListAdapter(this,predictedTaskItems);
        predictedListView.setAdapter(predictedTaskListAdapter);
    }
}
