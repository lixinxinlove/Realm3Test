package com.lee.realmtest.runnable;

import com.lee.realmtest.bean.EventEntity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by android on 2017/3/3.
 */
public class UpdateRunnable implements Runnable {


    @Override
    public void run() {

        Realm mRealm = null;
        try {
            mRealm = Realm.getDefaultInstance();
            final RealmResults<EventEntity> mData;
            mRealm.beginTransaction();
            mData = mRealm.where(EventEntity.class).findAll();
            for (int i = 0; i < mData.size(); i++) {
                mData.get(i).setCity("天津" + i);
            }
            mRealm.commitTransaction();
        } finally {
            if (mRealm != null) {
                mRealm.close();
            }
        }
    }
}
