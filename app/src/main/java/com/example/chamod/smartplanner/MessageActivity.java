package com.example.chamod.smartplanner;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamod.smartplanner.Fragments.DateFragment;
import com.example.chamod.smartplanner.Fragments.TimeFragment;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.Models.Contact;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Message;
import com.example.chamod.smartplanner.Models.Tasks.MessageTask;
import com.example.chamod.smartplanner.Models.Time;

import java.util.Calendar;

public class MessageActivity extends AppCompatActivity implements DateFragment.DateFragmentListener,TimeFragment.TimeFragmentListener{

    private static final int RESULT_PICK_CONTACT = 85;

    FragmentManager fm = getSupportFragmentManager();
    private DateFragment dateFragment;
    private TimeFragment timeFragment;



    private TextView textViewContact,textViewDate,textViewTime;
    private EditText txtMessagge;


    private Contact contact=null;
    private Date date=null;
    private Time time=null;


    private TaskHandler taskHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        taskHandler=TaskHandler.getInstance(this);


        textViewContact=(TextView)findViewById(R.id.textViewContact);
        textViewDate=(TextView)findViewById(R.id.textViewDate);
        textViewTime=(TextView)findViewById(R.id.textViewTime);
        txtMessagge=(EditText) findViewById(R.id.txtMessagge);

        //        initial date set up
        java.util.Date util_date=new java.util.Date(System.currentTimeMillis());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(util_date);

        date=new Date(calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.YEAR));
    }


//    save message tasks
    public void saveMessageTask(View view){
        if(isValidInputs()){
            Message message=new Message(txtMessagge.getText().toString().trim(),contact);
            MessageTask messageTask=new MessageTask(message,date,time);
            taskHandler.saveNewTask(messageTask);
        }
    }

    public void cancelTask(View view){
        startActivity(new Intent(this,NavigaterActivity.class));
    }


    public void pickContact(View v)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MessageActivity", "Failed to pick contact");
        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     * @param data
     */
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
//            // Set the value to the textviews
            contact=new Contact(name,phoneNo);

            textViewContact.setText(contact.getName() + " - " + contact.getPhone_no());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    validation
    private boolean isValidInputs(){
        if(txtMessagge.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please enter the message...!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(date==null){
            Toast.makeText(this, "Please select the date...!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(time==null){
            Toast.makeText(this, "Please select the time...!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(contact==null){
            Toast.makeText(this, "Please select the receiver...!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }






    public void showDatePicker(View view){
        dateFragment=new DateFragment();
        dateFragment.show(fm,"DateFragment");
    }
    public void showTimePicker(View view){
        timeFragment=new TimeFragment();
        timeFragment.show(fm,"TimeFragment");
    }


    @Override
    public void setDate(int year, int month, int day) {
        date=new Date(day,month,year);
        textViewDate.setText(date.getDateString());
    }

    @Override
    public void setTime(int hour, int min) {
        time=new Time(hour,min);
        textViewTime.setText(time.getTimeString());
    }
}
