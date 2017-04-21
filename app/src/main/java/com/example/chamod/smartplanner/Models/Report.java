package com.example.chamod.smartplanner.Models;

import com.example.chamod.smartplanner.ListItemModels.ReportTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chamod on 4/21/17.
 */

public class Report {

    private String date;
    private Float completion;

    private ArrayList<ReportTask> completed_tasks=new ArrayList<>();
    private ArrayList<ReportTask> incompleted_tasks=new ArrayList<>();

    public Report(JSONObject report_json) {
        try {
            this.date=report_json.get("date").toString();
            this.completion=Float.valueOf(report_json.get("completion").toString());

            JSONArray jsonArray_com_tasks=(JSONArray) report_json.get("completed_tasks");

            for(int i=0;i<jsonArray_com_tasks.length();i++){
                completed_tasks.add(new ReportTask(new JSONObject(jsonArray_com_tasks.get(i).toString()).get("desc").toString(),true));
            }

            JSONArray jsonArray_incom_tasks=(JSONArray)report_json.get("incompleted_tasks");

            for(int i=0;i<jsonArray_incom_tasks.length();i++){
                incompleted_tasks.add(new ReportTask(new JSONObject(jsonArray_incom_tasks.get(i).toString()).get("desc").toString(),false));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getDate() {
        return date;
    }

    public Float getCompletion() {
        return completion;
    }

    public ArrayList<ReportTask> getCompleted_tasks() {
        return completed_tasks;
    }

    public ArrayList<ReportTask> getIncompleted_tasks() {
        return incompleted_tasks;
    }
}
