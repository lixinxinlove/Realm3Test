package com.lee.realmtest.runnable;

import android.util.Log;

import com.lee.realmtest.bean.EventEntity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by android on 2017/3/3.
 */
public class QueryRunnable implements Runnable {


    @Override
    public void run() {
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm mRealm = Realm.getInstance(config);

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<EventEntity> mData = realm.where(EventEntity.class).findAll();
                for (EventEntity entity : mData) {
                    Log.e("QueryRunnable", "id-" + entity.getId() + "--city--" + entity.getCity());
                }
            }
        });
    }
}
