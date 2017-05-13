package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 5/13/17.
 */

public class Contact {
    private String name;
    private String phone_no;

    public Contact(String name, String phone_no) {
        this.name = name;
        this.phone_no = phone_no;
    }

    public String getName() {
        return name;
    }

    public String getPhone_no() {
        return phone_no;
    }
}
