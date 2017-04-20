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

public class ReportsActivity extends AppCompatActivity implements DateFragment.DateFragmentListener{

    TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        dateTextView=(TextView)findViewById(R.id.textViewDate);

//        set list view




    }


    public void showDatePicker(View v){
        FragmentManager fm=getSupportFragmentManager();
        DateFragment dateFragment=new DateFragment();
        dateFragment.show(fm,"DateFragment");
    }

    @Override
    public void setDate(int year, int month, int day) {
        Date date=new Date(day,month,year);
        dateTextView.setText(date.getDateString());


    }


    public void getReport(View view){
        ReportHandler.getInstance(this).requestReport();
    }
}
