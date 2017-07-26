package com.example.shivam.smsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Casino on 7/15/17.
 * Custom List Adapater for populating the contacts list
 * contains Firstname, LastName and Phonenumber
 */

public class ContactsListAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<Contacts> contacts;

    public ContactsListAdapter(Context context, ArrayList<Contacts> contacts) {
        super();
        this.context = context;
        this.contacts = contacts;

    }

    public class ContactHolder {

        TextView firstName, lastName;

    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.contactslistview, parent, false);
            holder = new ContactHolder();
            holder.firstName = (TextView) convertView.findViewById(R.id.firstName);
            holder.lastName = (TextView) convertView.findViewById(R.id.lastName);

            convertView.setTag(holder);
        } else {
            holder = (ContactHolder) convertView.getTag();
        }

        holder.firstName.setText(contacts.get(position).getFirstName());
        holder.lastName.setText(contacts.get(position).getLastName());
        return convertView;
    }
}
