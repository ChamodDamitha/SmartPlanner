package com.example.chamod.smartplanner.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 3/24/17.
 */

public class ReminderFragment extends DialogFragment {

    ReminderFragmentListener reminderFragmentListener;

    public interface ReminderFragmentListener {
        void setReminderBefore(long miliseconds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.reminder_fragment,container,false);

        final RadioButton radioBtnOnTime=(RadioButton)view.findViewById(R.id.radioBtnOnTime);
        final RadioButton radioBtn10min=(RadioButton)view.findViewById(R.id.radioBtn10min);
        final RadioButton radioBtn30min=(RadioButton)view.findViewById(R.id.radioBtn30min);
        final RadioButton radioBtn1hour=(RadioButton)view.findViewById(R.id.radioBtn1hour);
        final RadioButton radioBtn1day=(RadioButton)view.findViewById(R.id.radioBtn1day);
        RadioButton radioBtnCustom=(RadioButton)view.findViewById(R.id.radioBtnCustom);

        Button btnSaveRem=(Button)view.findViewById(R.id.btnSaveRem);
        Button btnCancelRem=(Button)view.findViewById(R.id.btnCancelRem);

        btnSaveRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioBtnOnTime.isChecked()){
                    reminderFragmentListener.setReminderBefore(0);
                }
                else if(radioBtn10min.isChecked()){
                    reminderFragmentListener.setReminderBefore(10*60*1000);
                }
                else if(radioBtn30min.isChecked()){
                    reminderFragmentListener.setReminderBefore(30*60*1000);
                }
                else if(radioBtn1hour.isChecked()){
                    reminderFragmentListener.setReminderBefore(60*60*1000);
                }
                else if(radioBtn1day.isChecked()){
                    reminderFragmentListener.setReminderBefore(24*60*60*1000);
                }
                else{



                }
            }
        });

        btnCancelRem.setOnClickListener(new View.OnClickListener() {
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
            reminderFragmentListener =(ReminderFragmentListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }
}
