package com.example.chamod.smartplanner.EventHandlers;

import java.util.ArrayList;

/**
 * Created by chamod on 3/28/17.
 */

public class RepeatTaskChangeEvent {
    private static RepeatTaskChangeEvent repeatTaskChangeEvent=null;

    public static RepeatTaskChangeEvent getInstance(){
        if(repeatTaskChangeEvent==null){
            repeatTaskChangeEvent=new RepeatTaskChangeEvent();
        }
        return repeatTaskChangeEvent;
    }

    private RepeatTaskChangeEvent(){

    }

    private ArrayList<RepeatTaskChangeEventListner> repeatTaskChangeEventListners=new ArrayList<>();

    public void addNewRepeatTaskChangeEventListner(RepeatTaskChangeEventListner repeatTaskChangeEventListner){
        repeatTaskChangeEventListners.add(repeatTaskChangeEventListner);
    }

    public void repeatTaskChangedEventOccured(){
        for(RepeatTaskChangeEventListner repeatTaskChangeEventListner:repeatTaskChangeEventListners){
            repeatTaskChangeEventListner.repeatTaskChanged();
        }
    }
}
