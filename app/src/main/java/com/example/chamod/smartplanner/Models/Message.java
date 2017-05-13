package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 3/26/17.
 */

public class Message {
    private int msg_id;
    private String content;
    private Contact receiver;

    public Message(String content, Contact receiver) {
        this.content = content;
        this.receiver=receiver;
    }

    public String getContent() {
        return content;
    }

    public Contact getReceiver() {
        return receiver;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }
}
