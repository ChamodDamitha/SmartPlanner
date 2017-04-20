package com.example.chamod.smartplanner.Handlers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chamod.smartplanner.Constants;
import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chamod on 4/20/17.
 */

public class ReportHandler {
    private Context context;

    private static ReportHandler reportHandler=null;

    private ReportHandler(Context context){
        taskDB=TaskDB.getInstance(context);
        this.context=context;
    }

    TaskDB taskDB;

    public static ReportHandler getInstance(Context context){
        if(reportHandler==null){
            reportHandler=new ReportHandler(context);
        }
        return reportHandler;
    }


    public JSONObject generateDailyData(Date date){
        ArrayList<Task> tasks=taskDB.getAllTasks(date);

        JSONArray jsonObjectFullTasks=new JSONArray();
        JSONArray jsonObjectLocationTasks=new JSONArray();
        JSONArray jsonObjectTimeTasks=new JSONArray();

        for(Task task:tasks){
            if(task.getType().equals(Constants.FULL_TYPE)){
                jsonObjectFullTasks.put(task.toJSON());
            }
            else if(task.getType().equals(Constants.LOCATION_TYPE)){
                jsonObjectLocationTasks.put(task.toJSON());
            }
            if(task.getType().equals(Constants.TIME_TYPE)){
                jsonObjectTimeTasks.put(task.toJSON());
            }
        }

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("date",date.toString());
            jsonObject.put("day",date.getDayOfWeek());

            jsonObject.put("fulltasks",jsonObjectFullTasks);
            jsonObject.put("locationtasks",jsonObjectLocationTasks);
            jsonObject.put("timetasks",jsonObjectTimeTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("ORM",jsonObject.toString());

        return jsonObject;
    }


    public void requestReport(){

        String url="http://192.168.43.35:3000/";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("ORM",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ORM",error.toString());
            }
        });



        AppController.getInstance(context).addToRequestQueue(jsonObjectRequest,"json_req_tag");

    }

}
