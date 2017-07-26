package com.example.shivam.smsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Casino on 7/17/17.
 */

public class MessagesFragment extends android.support.v4.app.Fragment {

    private ListView messagesList;
    private ArrayList MessagesArrayList;
    private MessageListAdapter messageListAdapter;
    Realm realm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messages_fragment, container, false);

        messagesList = (ListView) view.findViewById(R.id.messageList);

        // Instantiating the realm database
        realm = Realm.getDefaultInstance();

        populateList();

        messageListAdapter = new MessageListAdapter(getActivity(), MessagesArrayList);
        messagesList.setAdapter(messageListAdapter);

        return view;
    }

    // Function to refresh the message list whenever the user gets to the messages tab
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            populateList();
        }
    }

// Function to retrieve the data from database and inserting that to the messages list
    public void populateList() {
        MessagesArrayList = new ArrayList<>();

        RealmResults<ContactsData> realmResults = realm.where(ContactsData.class).findAllSorted("messageTime", Sort.DESCENDING);
        for(int i =0; i<realmResults.size(); i++)   {
            MessagesArrayList.add(new Messages(realmResults.get(i).getFirstName(), realmResults.get(i).getLastName(), realmResults.get(i).getMessageText(), realmResults.get(i).getMessageTime()));
//            Log.i(realmResults.get(i).getFirstName(), realmResults.get(i).getMessageText() + realmResults.get(i).getMessageTime());
        }

        messageListAdapter = new MessageListAdapter(getActivity(), MessagesArrayList);
        messagesList.setAdapter(messageListAdapter);
    }
}
