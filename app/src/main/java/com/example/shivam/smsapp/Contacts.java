package com.example.shivam.smsapp;

/**
 * Created by Casino on 7/15/17.
 * Custom Class for contacts consisits of firstName, lastName, phoneNumber
 */

public class Contacts {

    private String firstName, lastName;
    private long phoneNumber;

    public Contacts(String firstName, String lastName, long phoneNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
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

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
