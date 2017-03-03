package com.lee.realmtest.runnable;

import com.lee.realmtest.bean.EventEntity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by android on 2017/3/3.
 */
public class UpdateRunnable implements Runnable {


    @Override
    public void run() {

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm mRealm = Realm.getInstance(config);

      //  Log.e("lee", "save---耗时：" + (eee - sss));
        final RealmResults<EventEntity> mData;
        mRealm.beginTransaction();
        mData = mRealm.where(EventEntity.class).findAll();
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).setCity("天津"+i);
        }
        mRealm.commitTransaction();


//        final RealmResults<EventEntity> mData;
//        mData = mRealm.where(EventEntity.class).findAll();
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                for (int i = 0; i < mData.size(); i++) {
//                    mData.get(i).setCity("天津");
//                }
//            }
//        });

    }
}
