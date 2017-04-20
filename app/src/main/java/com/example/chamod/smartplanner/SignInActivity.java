package com.example.chamod.smartplanner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    UserDB userDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userDB=UserDB.getInstance(this);

        if(userDB.getUser()!=null){
            Intent intent=new Intent(this,NavigaterActivity.class);
            startActivity(intent);
        }
    }

    public void Signin(View view){

        String url="http://192.168.43.35:3000/addUser";

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Connecting to server");
        progressDialog.show();


        final String email="chamod@gmail.com";
        final String name="chamod";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url,null,new Response.Listener<JSONObject>() {
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", "chamod");
                params.put("email", "chamod@gmail.com");

                return params;
            }
        };
        AppController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
