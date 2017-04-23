package com.example.chamod.smartplanner;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamod.smartplanner.EventHandlers.ReportArrivedListner;
import com.example.chamod.smartplanner.Fragments.DateFragment;
import com.example.chamod.smartplanner.Handlers.ReportHandler;
import com.example.chamod.smartplanner.ListItemModels.ReportTask;
import com.example.chamod.smartplanner.ListItemModels.ReportsListAdapter;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Report;

import java.util.ArrayList;
import java.util.Calendar;

public class ReportsActivity extends AppCompatActivity implements DateFragment.DateFragmentListener,ReportArrivedListner{

    Date selected_date=null;

    TextView dateTextView;

    ReportHandler reportHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        dateTextView=(TextView)findViewById(R.id.textViewDate);


        reportHandler=ReportHandler.getInstance(this);
        reportHandler.addReportArriveListner(this);



//        set today
        Calendar calendar=Calendar.getInstance();
        selected_date = new Date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));

        dateTextView.setText(selected_date.getDateString());

        checkReport();
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
        checkReport();
    }

    private void checkReport(){
        if(selected_date!=null) {
            ReportHandler.getInstance(this).requestReport(selected_date);
        }
    }

    public void getReport(View view){
        checkReport();
    }
    public void sendDailyData(View view){
        if(selected_date!=null) {
            ReportHandler.getInstance(this).sendDailyData(selected_date);
        }
    }

    @Override
    public void reportArrived(Report report) {

        TextView taskCompletion = (TextView) findViewById(R.id.taskCompletion);
        LinearLayout completionPanel = (LinearLayout) findViewById(R.id.completionPanel);
        ListView listViewReportTasks = (ListView) findViewById(R.id.listViewReportTasks);
        if(report==null){
            completionPanel.setVisibility(View.GONE);
            listViewReportTasks.setVisibility(View.GONE);
        }
        else {

            taskCompletion.setText(report.getCompletion() + "%");

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarCompletion);

            progressBar.setMax(100);
            progressBar.setProgress(Math.round(report.getCompletion()));

            completionPanel.setVisibility(View.VISIBLE);


            ArrayList<ReportTask> reportTasks = new ArrayList<>();

            int i = 0;
            for (ReportTask reportTask : report.getCompleted_tasks()) {
                if (i == 0) {
                    reportTask.setFirstOne(true);
                }
                reportTasks.add(reportTask);
                i++;
            }
            i = 0;
            for (ReportTask reportTask : report.getIncompleted_tasks()) {
                if (i == 0) {
                    reportTask.setFirstOne(true);
                }
                reportTasks.add(reportTask);
                i++;
            }

            listViewReportTasks.setVisibility(View.VISIBLE);
            ReportsListAdapter completedTasksAdapter = new ReportsListAdapter(this, reportTasks);
            listViewReportTasks.setAdapter(completedTasksAdapter);
        }
    }
}
