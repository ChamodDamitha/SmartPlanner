package com.example.chamod.smartplanner;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chamod.smartplanner.Fragments.DateFragment;
import com.example.chamod.smartplanner.Handlers.ReportHandler;
import com.example.chamod.smartplanner.ListItemModels.ReportsListAdapter;
import com.example.chamod.smartplanner.Models.Date;

import java.util.Calendar;

public class ReportsActivity extends AppCompatActivity implements DateFragment.DateFragmentListener{

    Date selected_date=null;

    TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        dateTextView=(TextView)findViewById(R.id.textViewDate);


//        set today
        Calendar calendar=Calendar.getInstance();
        selected_date = new Date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));


    }


    public void showDatePicker(View v){
        FragmentManager fm=getSupportFragmentManager();
        DateFragment dateFragment=new DateFragment();
        dateFragment.show(fm,"DateFragment");
    }

    @Override
    public void setDate(int year, int month, int day) {
        selected_date=new Date(day,month,year);
        dateTextView.setText(selected_date.getDateString());


    }


    public void getReport(View view){
        if(selected_date!=null) {
            ReportHandler.getInstance(this).requestReport(selected_date);
        }
    }
}
