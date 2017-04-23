package com.example.chamod.smartplanner.Handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chamod.smartplanner.Constants;
import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Database.UserDB;
import com.example.chamod.smartplanner.EventHandlers.ReportArrivedListner;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Report;
import com.example.chamod.smartplanner.Models.Tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chamod on 4/20/17.
 */

public class ReportHandler {
    ArrayList<ReportArrivedListner> reportArrivedListners=new ArrayList<>();

    private Context context;

    private static ReportHandler reportHandler=null;

    private ReportHandler(Context context){
        taskDB=TaskDB.getInstance(context);
        userDB=UserDB.getInstance(context);

        this.context=context;
    }

    TaskDB taskDB;
    UserDB userDB;

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


    public void requestReport(Date date){
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Report is loading...");
        progressDialog.show();

        String url="http://192.168.43.35:3000/getDailyReport";

        JSONObject params=new JSONObject();

        try {
            params.put("user_email",userDB.getUser().getEmail());
            params.put("date",date.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.hide();
                try {
                    if((boolean)response.get("success")) {
//                        Toast.makeText(context, response.get("msg").toString(), Toast.LENGTH_LONG).show();
                        if(response.get("msg")!=null) {
                            notifyReportArrived(new JSONObject(response.get("msg").toString()));
                        }
                    }
                    else{
                        Toast.makeText(context, response.get("msg").toString(), Toast.LENGTH_LONG).show();
                        notifyNoReport();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, "No data available...!", Toast.LENGTH_LONG).show();
                    notifyNoReport();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(context, "Getting report failed...!", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance(context).addToRequestQueue(jsonObjectRequest,"json_req_tag");

    }


    public void sendDailyData(Date date){

        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Data is sending...");
        progressDialog.show();

        String url="http://192.168.43.35:3000/saveDailyData";

        JSONObject params=new JSONObject();

        try {
            params.put("user_email",userDB.getUser().getEmail());
            params.put("daily_data",generateDailyData(date));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.hide();
                try {
                    if((boolean)response.get("success")) {
                        Toast.makeText(context, response.get("msg").toString(), Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, response.get("msg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(context, "Sending data failed...!", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance(context).addToRequestQueue(jsonObjectRequest,"json_req_tag");

    }


    public void addReportArriveListner(ReportArrivedListner reportArrivedListner){
        reportArrivedListners.add(reportArrivedListner);
    }

    private void notifyNoReport(){
        for(ReportArrivedListner reportArrivedListner:reportArrivedListners){
            reportArrivedListner.reportArrived(null);
        }
    }

    private void notifyReportArrived(JSONObject report_json){
//        Toast.makeText(context,report_json.toString(),Toast.LENGTH_LONG).show();
        Report report=new Report(report_json);
        for(ReportArrivedListner reportArrivedListner:reportArrivedListners){
            reportArrivedListner.reportArrived(report);
        }
    }

}
