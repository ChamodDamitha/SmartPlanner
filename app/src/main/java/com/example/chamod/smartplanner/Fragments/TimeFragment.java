package com.example.chamod.smartplanner.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 2/17/17.
 */

public class TimeFragment extends Fragment {

    Button btnOK,btnCancel;
    TimePicker timePicker;

    TimeFragmentListener timeFragmentListener;

    public interface TimeFragmentListener{
         void setTime(int hour,int min);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.time_fragment,container,false);

        btnOK=(Button)view.findViewById(R.id.btnOKTime);
        btnCancel=(Button)view.findViewById(R.id.btnCancelTime);
        timePicker=(TimePicker)view.findViewById(R.id.timePicker2);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFragmentListener.setTime(timePicker.getHour(),timePicker.getMinute());
                view.setVisibility(View.GONE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            timeFragmentListener=(TimeFragmentListener)activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }


}
