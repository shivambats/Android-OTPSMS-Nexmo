package com.example.shivam.smsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Casino on 7/15/17.
 */

public class MessageListAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<Messages> messages;

    public MessageListAdapter(Context context, ArrayList<Messages> messages) {
        super();
        this.context = context;
        this.messages = messages;

    }

    public class ContactHolder {

        TextView firstName, lastName, messageText, date;

    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.messagelistview, parent, false);
            holder = new ContactHolder();
            holder.firstName = (TextView) convertView.findViewById(R.id.firstNameMessage);
            holder.lastName = (TextView) convertView.findViewById(R.id.lastNameMessage);
            holder.messageText = (TextView) convertView.findViewById(R.id.messageText);
            holder.date = (TextView) convertView.findViewById(R.id.messageTime);

            convertView.setTag(holder);
        } else {
            holder = (ContactHolder) convertView.getTag();
        }

        holder.firstName.setText(messages.get(position).getFirstName());
        holder.lastName.setText(messages.get(position).getLastName());
        String unixTime = messages.get(position).getTime();

        //converting unixtime into corresponding date
        String time=new java.util.Date(Long.parseLong(unixTime)*1000).toString();


        time = time.substring(0,19) + time.substring(29,34);

        holder.date.setText(String.valueOf(time));
        holder.messageText.setText(messages.get(position).getMessage());
        return convertView;
    }
}
