package com.example.shivam.smsapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Casino on 7/17/17.
 */

public class BaseApplication extends Application {

    RealmConfiguration config;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        config = new RealmConfiguration.Builder().build();

        Realm.setDefaultConfiguration(config);
    }
}
