package com.example.chamod.smartplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chamod.smartplanner.ListItemModels.PredictedTaskListAdapter;
import com.example.chamod.smartplanner.ListItemModels.TaskItem;

public class PredictedScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predicted_schedule);

        //set list view
        TaskItem[] predictedTaskItems=new TaskItem[3];
        predictedTaskItems[0]=new TaskItem("Meeting x","9.00 AM","Matara");
        predictedTaskItems[1]=new TaskItem("Meeting y","1.00 PM","Colombo");
        predictedTaskItems[2]=new TaskItem("Meeting z","3.00 PM","Galle");

        ListView predictedListView=(ListView)findViewById(R.id.predictedTasksListView);
        ArrayAdapter predictedTaskListAdapter=new PredictedTaskListAdapter(this,predictedTaskItems);
        predictedListView.setAdapter(predictedTaskListAdapter);
    }
}
