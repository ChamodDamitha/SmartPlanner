package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 3/26/17.
 */

public class Message {
    private int msg_id;
    private String content;
    private String receiver_no;

    public Message(String content, String receiver_no) {
        this.content = content;
        this.receiver_no = receiver_no;
    }

    public String getContent() {
        return content;
    }

    public String getReceiver_no() {
        return receiver_no;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }
}
