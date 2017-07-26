package com.example.shivam.smsapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Casino on 7/17/17.
 */

public class ContactsData extends RealmObject {

    private String firstName;
    private String lastName;
    private String messageText;
    private String messageTime;

    @PrimaryKey
    private int id;

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

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
