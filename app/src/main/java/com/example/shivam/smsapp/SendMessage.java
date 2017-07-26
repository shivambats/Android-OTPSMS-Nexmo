package com.example.shivam.smsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class SendMessage extends AppCompatActivity {

    private TextView messageText;
    private Button sendButton;

    private SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreference;

    String your_api_key;
    String your_api_secret;

    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editor = mSharedPreference.edit();

        // API keys for NEXMO
        your_api_key = "your_api_key";
        your_api_secret = "your_api_secret_key";

        messageText = (TextView) findViewById(R.id.messageContent);
        sendButton = (Button) findViewById(R.id.sendMessage);

        realm = Realm.getDefaultInstance();

        // Generating a random 6 digits number for the OTP
        Random ran = new Random();
        final int n = ran.nextInt(999999) + 100000;

        //Setting the messageText field with the OTP we generated
        messageText.setText("Hi.. Your OTP is "+String.valueOf(n));

        //setting action for the send button on clicking
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(n);
            }
        });
    }

    // function for checking the internet connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void sendMessage(int n)   {

        // first checking the device has internet connectivity or not, if yes then an api request is made
        // The api request is a post method, with api_key, secret_api, from, to, text as params
        // If the api is a testing one, the number is to should be added to the Nexmo's panel as Testing Numbers
        if(isNetworkAvailable()==true) {

            StringRequest request = new StringRequest(Request.Method.POST, "https://rest.nexmo.com/sms/json?", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message_count = jsonObject.getString("message-count");
                        JSONArray messages = jsonObject.getJSONArray("messages");
                        String n = messages.getString(0);
                        jsonObject = new JSONObject(n);
                        String status = jsonObject.getString("status");

                        // If the message has been sent successfully, it gives status in the response as 0, hence checking the status code,
                        // if the status is equal to 0 Toaster will be shown for success
                        if(status.equalsIgnoreCase("0")){

                            // Getting the current time in unix timestamp
                            long unixTime = System.currentTimeMillis() / 1000L;

                            // Savetodatabase for saving the message info into phone's memory
                            saveToDatabase(mSharedPreference.getString("FirstName", "Test"), mSharedPreference.getString("LastName", "Test"), messageText.getText().toString(), String.valueOf(unixTime));
                            Toast.makeText(SendMessage.this, "Message Sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equalsIgnoreCase("1")){
                            Toast.makeText(SendMessage.this, "Please wait for sometime, Limit reached", Toast.LENGTH_SHORT).show();

                        }
                        else if(status.equalsIgnoreCase("29")){
                            Toast.makeText(SendMessage.this, "Add Number as a test number on Nexmo Panel", Toast.LENGTH_SHORT).show();

                        }
                        else if(status.equalsIgnoreCase("2")){
                            Toast.makeText(SendMessage.this, "Please try again after sometime", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(SendMessage.this, "Couldn't send the message", Toast.LENGTH_SHORT).show();
                            String xyz = jsonObject.getString("error-text");
                            Log.e("error",xyz);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SendMessage.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();

                    // parameters for the api call as api_key, api_secret (Both we get from the Nexmo account)
                    // from - Name appears on the text message to the receipient
                    // to is the phone number for sending the message

                    map.put("api_key", your_api_key);
                    map.put("api_secret", your_api_secret);
                    map.put("from", "ShivamTest");
                    map.put("to", mSharedPreference.getString("PhoneNumber", "919899813094"));
                    map.put("text", messageText.getText().toString());

                    return map;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(SendMessage.this);
            requestQueue.add(request);
        }

        // If the user is not connected to the internet, Toaster will be shown for connecting the internet
        else {

            Toast.makeText(SendMessage.this,"Connect to the Internet", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }


    // Function to save the message information to Realm database with primary key as the time
    private void saveToDatabase(final String firstName, final String lastName, final String messageText, final String messageTime) {

        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm bgrealm) {
                                              ContactsData contactsData = bgrealm.createObject(ContactsData.class, messageTime);
                                              contactsData.setFirstName(firstName);
                                              contactsData.setLastName(lastName);
                                              contactsData.setMessageText(messageText);
                                              contactsData.setMessageTime(messageTime);
                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {

                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                          }
                                      }

        );

    }


}
