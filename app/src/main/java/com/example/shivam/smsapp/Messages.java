package com.example.shivam.smsapp;

/**
 * Created by Casino on 7/17/17.
 */

public class Messages {
    private String message, time, firstName, lastName;

    public Messages(String firstName, String lastName, String message, String time){
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
