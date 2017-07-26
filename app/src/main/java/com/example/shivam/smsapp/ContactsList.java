package com.example.shivam.smsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Casino on 7/15/17.
 */

public class ContactsList extends android.support.v4.app.Fragment {

    private ListView contactsList;
    private ArrayList<Contacts> ContactsArrayList;
    ContactsListAdapter contactsListAdapter;

    private SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreference;

    JSONObject json;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, container, false);

        contactsList = (ListView) view.findViewById(R.id.contactsList);
        // getting the refernce of contactsList from contacts_fragments

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        editor = mSharedPreference.edit();

        // Creating an arraylist of type Contacts which will take 3 params, FirstName, LastName, and phoneNumber

        JSONObject jsonObject = makeJSON();

        populateContacts(jsonObject);

        /* Setting action for the elements in the ContactsList,
         * after clicking the element the action will get the FirstName, LastName, and PhoneNumber
         * of that particular element and the firstName, lastName, and phoneNumber will get stored locally
         * so that can be communicated with other activities
        */
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contacts contacts = ContactsArrayList.get(position);

                //Getting the firstName, lastName, and phoneNumber from the tapped list Item
                String firstName = contacts.getFirstName();
                String lastName = contacts.getLastName();
                long contactNumber = contacts.getPhoneNumber();

                //Setting the sharedpreference by the values fetched
                editor.putString("FirstName", firstName);
                editor.putString("LastName", lastName);
                editor.putString("PhoneNumber", String.valueOf(contactNumber));

                editor.commit();

                // Starting the activity ContactInfo for displaying the contact info
                Intent i = new Intent(getActivity(), ContactInfo.class);
                startActivity(i);

            }


        });

        return view;
    }

    public JSONObject makeJSON()  {
        json = new JSONObject();

        try {
            json.accumulate("FirstName", "Shivam");
            json.accumulate("FirstName", "Bats");
            json.accumulate("LastName", "Batra");
            json.accumulate("PhoneNumber", "919899813094");
            json.accumulate("PhoneNumber", "919899813094");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public void populateContacts(JSONObject jsonObject) {

        try {
            JSONArray firstNameArray = jsonObject.getJSONArray("FirstName");
            JSONArray lastNameArray = jsonObject.getJSONArray("LastName");
            JSONArray phoneNumberArray = jsonObject.getJSONArray("PhoneNumber");

            ContactsArrayList = new ArrayList<>();

            for(int i =0 ;i<firstNameArray.length(); i++) {
                ContactsArrayList.add(new Contacts(firstNameArray.get(i).toString(), lastNameArray.get(i).toString(), Long.parseLong(phoneNumberArray.get(i).toString())));
            }
            // Populating the list with the contactsListAdapter, a custom adapter for the custom list for contacts
            contactsListAdapter = new ContactsListAdapter(getActivity(), ContactsArrayList);
            contactsList.setAdapter(contactsListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
