package com.lee.realmtest.runnable;

import android.util.Log;

import com.lee.realmtest.bean.EventEntity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by android on 2017/3/3.
 */
public class QueryRunnable implements Runnable {

    @Override
    public void run() {
        Realm mRealm = null;
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            RealmResults<EventEntity> mData = mRealm.where(EventEntity.class).findAll();
            for (EventEntity entity : mData) {
                Log.e("QueryRunnable", "id-" + entity.getId() + "--city--" + entity.getCity());
            }
            mRealm.commitTransaction();
        } finally {
            if (mRealm != null) {
                mRealm.close();
            }
        }
    }
}
