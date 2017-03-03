package com.example.chamod.smartplanner.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.chamod.smartplanner.R;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by chamod on 2/21/17.
 */

public class DateFragment extends DialogFragment {
    DateFragmentListener dateFragmentListener;


    Button btnSaveDate,btnCancelDate;
    DatePicker datePicker;


    public interface DateFragmentListener{
        void setDate(int year,int month,int day );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_fragment, container,
                false);
//        getDialog().setTitle("DialogFragment Tutorial");
        // Do something else


        btnSaveDate=(Button)view.findViewById(R.id.btnSaveDate);
        btnCancelDate=(Button)view.findViewById(R.id.btnCancelDate);

        datePicker=(DatePicker) view.findViewById(R.id.datePicker);



        btnSaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateFragmentListener.setDate(datePicker.getYear(),datePicker.getMonth()+1,
                        datePicker.getDayOfMonth());
                getDialog().dismiss();
            }
        });

        btnCancelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }






    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            dateFragmentListener=(DateFragmentListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

}
