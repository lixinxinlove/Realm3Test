package com.lee.realmtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lee.realmtest.bean.EventEntity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnSave;
    private Button btnFill;
    private Button btnDel;
    private TextView tvText;

    private List<EventEntity> mData;
    private List<EventEntity> lists;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnSave.setOnClickListener(this);
        btnFill.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        realm = Realm.getDefaultInstance();

        initData();

    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            EventEntity entity = new EventEntity(10000 + i, "89000" + i, "100000" + i, "标题" + i, "", "www.baidu.com", "北京" + i);
            mData.add(entity);
        }
    }

    private void initView() {
        btnSave = (Button) findViewById(R.id.btn_save);
        btnFill = (Button) findViewById(R.id.btn_fill);
        btnDel = (Button) findViewById(R.id.btn_delete);
        tvText = (TextView) findViewById(R.id.tv_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_fill:
                fill();
                break;
            case R.id.btn_delete:
                delete();
                break;
        }
    }

    private void delete() {
        final RealmResults<EventEntity> evenet = realm.where(EventEntity.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //删除所有数据
                evenet.deleteAllFromRealm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void save() {
        realm.beginTransaction();
        long s = System.currentTimeMillis();
        realm.insert(mData);
        long e = System.currentTimeMillis();
        Log.e("lee", "save耗时：" + (e - s));
        realm.commitTransaction();
    }

    long ss;

    private void fill() {
        ss = System.currentTimeMillis();
        RealmResults<EventEntity> list = realm.where(EventEntity.class).between("id", 10000, 10100).findAll();
        //  list.addChangeListener(callback);
//        if (list.isLoaded()){
//            list.removeAllChangeListeners();
//        }

        long ee = System.currentTimeMillis();
        Log.e("lee", "fill耗时：" + (ee - ss));
        tvText.setText("耗时：" + (ee - ss));
        StringBuilder sb = new StringBuilder();
        for (EventEntity entity : list) {
            sb.append(entity.getId());
            sb.append(entity.getTitle());
            sb.append(entity.getCity());
            sb.append("\n");
        }
        tvText.setText(sb.toString());
    }

    private RealmChangeListener callback = new RealmChangeListener<RealmResults<EventEntity>>() {
        @Override
        public void onChange(RealmResults<EventEntity> element) {
            long ee = System.currentTimeMillis();
            Log.e("lee", "fill耗时：" + (ee - ss));
            tvText.setText("耗时：" + (ee - ss));
            StringBuilder sb = new StringBuilder();
            for (EventEntity entity : element) {
                sb.append(entity.getId());
                sb.append(entity.getTitle());
                sb.append(entity.getCity());
                sb.append("\n");
            }
            tvText.setText(sb.toString());
        }
    };
}
