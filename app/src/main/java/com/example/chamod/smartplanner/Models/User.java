package com.example.chamod.smartplanner.Models;

/**
 * Created by chamod on 4/20/17.
 */

public class User {
    private String email;
    private String name;
    private String phone;

    public User(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
