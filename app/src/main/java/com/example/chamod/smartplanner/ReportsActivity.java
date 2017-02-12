package com.example.chamod.smartplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chamod.smartplanner.ListItemModels.ReportsListAdapter;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

//        set list view
        String[] items={"Mostly Visited","Mostly Met","Mostly Did"};
        ListView reportsListView=(ListView)findViewById(R.id.listViewReports);
        ArrayAdapter<String> reportsListAdapter=new ReportsListAdapter(this,items);
        reportsListView.setAdapter(reportsListAdapter);

    }
}
