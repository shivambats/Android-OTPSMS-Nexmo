package com.example.shivam.smsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContactInfo extends AppCompatActivity {

    private TextView firstName, lastName, phoneNumber;

    private Button messageButton;

    private SharedPreferences mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());

        // Getting the references of the firstName, lastName, phoneNumber, and messageButton from activity_contact_info
        firstName = (TextView) findViewById(R.id.contactInfoFirstName);
        lastName = (TextView) findViewById(R.id.contactInfoLastName);
        phoneNumber = (TextView) findViewById(R.id.contactInfoContactNumber);
        messageButton = (Button) findViewById(R.id.message);

        // Setting the firstname, lastname and phonenumber sharedpreference to communicate with other activities
        firstName.setText(mSharedPreference.getString("FirstName", "FirstName"));
        lastName.setText(mSharedPreference.getString("LastName", "LastName"));
        phoneNumber.setText(mSharedPreference.getString("PhoneNumber", "98998989898"));

        // messageButton action for starting the SendMessage activity where the user will see OTP message
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactInfo.this, SendMessage.class);
                startActivity(i);
            }
        });
    }

}
