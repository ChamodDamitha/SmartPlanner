package com.example.chamod.smartplanner.Models.Tasks;

import com.example.chamod.smartplanner.Constants;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Message;

/**
 * Created by chamod on 3/26/17.
 */

public class MessageTask extends Task {
    private Message message;

    public MessageTask(String description, Date date,Message message) {
        super(description, date);
        this.message=message;
        this.type= Constants.MESSAGE_TYPE;
    }

    public Message getMessage() {
        return message;
    }
}
