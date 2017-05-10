package com.example.chamod.smartplanner.Handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Database.UserDB;
import com.example.chamod.smartplanner.EventHandlers.PredictedTasksListener;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chamod on 5/10/17.
 */

public class ScheduleHandler {
    private Context context;

    private static ScheduleHandler scheduleHandler=null;

    public static ScheduleHandler getInstance(Context context){
        if(scheduleHandler==null){
            scheduleHandler=new ScheduleHandler(context);
        }
        return scheduleHandler;
    }

    private UserDB userDB;
    private TaskDB taskDB;


    private ScheduleHandler(Context context){
        this.context=context;
        userDB=UserDB.getInstance(context);
        taskDB=TaskDB.getInstance(context);
    }




    private ArrayList<PredictedTasksListener> predictedTasksListeners=new ArrayList<>();

    public void addPredictedTaskListener(PredictedTasksListener predictedTasksListener){
        predictedTasksListeners.add(predictedTasksListener);
    }


    public void requestPredictedTasks(final Date date){
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Schedule is loading...");
        progressDialog.show();

        String url="http://192.168.43.35:3000/getPredictedSchedule";

        JSONObject params=new JSONObject();

        try {
            params.put("email",userDB.getUser().getEmail());
            params.put("day",date.getDayOfWeek());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.hide();
                try {
                    if((boolean)response.get("success")) {
                        if(response.get("msg")!=null) {
                            notifyScheduleReceived(new JSONObject(response.get("msg").toString()),date);
                        }
                    }
                    else{
                        Toast.makeText(context, response.get("msg").toString(), Toast.LENGTH_LONG).show();
                        notifyNoSchedule();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, "No data available...!", Toast.LENGTH_LONG).show();
                    notifyNoSchedule();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(context, "Getting schedule failed...!", Toast.LENGTH_LONG).show();
                notifyNoSchedule();
            }
        });

        AppController.getInstance(context).addToRequestQueue(jsonObjectRequest,"json_req_tag");

    }

    private void notifyScheduleReceived(JSONObject data,Date date) {
//        Toast.makeText(context,data.toString(),Toast.LENGTH_LONG).show();

//        convert to tasks
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            JSONArray jsonArray_tasks = (JSONArray) data.get("tasks");
            for (int i = 0; i < jsonArray_tasks.length(); i++) {
                JSONObject json_task = new JSONObject(jsonArray_tasks.get(i).toString());

                if (json_task.get("time").equals(null)) {
                    Location location = new Location(json_task.getJSONObject("task").getJSONObject("location"));

                    LocationTask locationTask = new LocationTask(json_task.getJSONObject("task").getString("desc"),
                            date, location);
                    tasks.add(locationTask);

                } else {
                    if (json_task.getJSONObject("task").isNull("location")) {

                        Time time = new Time(json_task.getJSONObject("task").getString("time"));

                        TimeSet timeSet = new TimeSet(date, time, time);

                        TimeTask timeTask = new TimeTask(json_task.getJSONObject("task").getString("desc"), date, timeSet);
                        tasks.add(timeTask);

                    } else {
                        Location location = new Location(json_task.getJSONObject("task").getJSONObject("location"));
                        Time time = new Time(json_task.getJSONObject("task").getString("time"));

                        TimeSet timeSet = new TimeSet(date, time, time);

                        FullTask fullTask = new FullTask(json_task.getJSONObject("task").getString("desc"), date, location, timeSet);
                        tasks.add(fullTask);
                    }
                }

            }
//          notify activities
            for (PredictedTasksListener predictedTasksListener : predictedTasksListeners) {
                predictedTasksListener.predictedTasksReceived(tasks);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void notifyNoSchedule(){
        for (PredictedTasksListener predictedTasksListener : predictedTasksListeners) {
            predictedTasksListener.notifyNoSchedule();
        }
    }

}
