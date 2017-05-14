package com.example.chamod.smartplanner;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.chamod.smartplanner.Database.UserDB;
import com.example.chamod.smartplanner.Handlers.AppController;
import com.example.chamod.smartplanner.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private GoogleApiClient googleApiClient=null;
    private GoogleSignInOptions signInOptions;


    UserDB userDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        requestPermissions();

    }

    private void initialize(){
        userDB=UserDB.getInstance(this);

        if(userDB.getUser()!=null){
            Intent intent=new Intent(this,NavigaterActivity.class);
            startActivity(intent);
        }
    }

    private void requestPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    Constants.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},
                    Constants.MY_PERMISSIONS_REQUEST_READ_SMS);
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    Constants.MY_PERMISSIONS_REQUEST_SEND_SMS);
            return;
        }

        initialize();
    }


    //    ...............................Permission response..........................................

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Constants.MY_PERMISSIONS_REQUEST_READ_CONTACTS:{
                requestPermissions();
            }
            case Constants.MY_PERMISSIONS_REQUEST_READ_SMS:{
                requestPermissions();
            }
            case Constants.MY_PERMISSIONS_REQUEST_SEND_SMS:{
                requestPermissions();
            }
        }
    }


    public void Signin(View view){

        String url="http://192.168.43.35:3000/addUser";


        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Connecting to server");
        progressDialog.show();

//google signin
        final String email="chamod@gmail.com";
        final String name="chamod";


        JSONObject params=new JSONObject();
        try {
            params.put("name", name);
            params.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url,params,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.hide();
                try {
                    if((boolean)response.get("success")){

//                        add the user to local db
                        TelephonyManager tMgr = (TelephonyManager) SignInActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
                        String mPhoneNumber = tMgr.getLine1Number();
                        User user=new User(email,name,mPhoneNumber);
                        userDB.addUser(user);

                        startActivity(new Intent(SignInActivity.this,NavigaterActivity.class));
                    }
                    Toast.makeText(SignInActivity.this,response.get("msg").toString(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(SignInActivity.this,"Signing falied...!",Toast.LENGTH_LONG).show();
                Log.e("ORM", error.toString());
            }
        });

        AppController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


}
