package com.lee.realmtest;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by android on 2017/3/2.
 */
public class App extends Application {


    public static Realm realm2;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("lee.realm")
                .build();
        Realm.setDefaultConfiguration(config);


        RealmConfiguration config2 = new RealmConfiguration.Builder()
                .name("lee2.realm")
                .build();

        realm2 = Realm.getInstance(config2);
    }
}
