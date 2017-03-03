package com.lee.realmtest;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by android on 2017/3/2.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .rxFactory()
//                .build();

        Realm.setDefaultConfiguration(config);


    }
}
