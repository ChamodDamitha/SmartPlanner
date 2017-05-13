package com.example.chamod.smartplanner.Models.Tasks;

import com.example.chamod.smartplanner.Constants;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Message;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeSet;

import org.json.JSONObject;

/**
 * Created by chamod on 3/26/17.
 */

public class MessageTask extends Task {
    private Message message;
    private TimeSet timeSet;

    public MessageTask(Message message,Date date,TimeSet timeSet) {
        super(message.getContent(), date);
        this.message=message;
        this.type= Constants.MESSAGE_TYPE;
        this.timeSet=timeSet;
    }

    public Message getMessage() {
        return message;
    }


    public TimeSet getTimeSet() {
        return timeSet;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
