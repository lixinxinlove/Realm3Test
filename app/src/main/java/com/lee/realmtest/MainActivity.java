package com.lee.realmtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.realmtest.bean.EventEntity;
import com.lee.realmtest.runnable.QueryRunnable;
import com.lee.realmtest.runnable.UpdateRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private Button btnFill;
    private Button btnDel;
    private Button btnAggregation;
    private Button btnUpdate;
    private Button btnCleanAll;
    private Button btnQuery;
    private Button btnThread;
    private TextView tvText;

    private List<EventEntity> mData;

    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        initView();
        initData();
    }

    private void initData() {

        long id;
        String eid;
        String event_id;
        String title;
        String sub_title;
        String detail_url;
        String city;
        Random random = new Random();
        mData = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            id = 10000 + i;
            eid = "T" + i;
            event_id = "G" + i;
            title = "标题";
            sub_title = "副标题" + random.nextInt(10);
            detail_url = "www.baidu." + random.nextInt(10);
            city = "北京" + random.nextInt(10);
            EventEntity entity = new EventEntity(id, eid, event_id, title, sub_title, detail_url, city);
            mData.add(entity);
        }
    }

    private void initView() {

        tvText = (TextView) findViewById(R.id.tv_text);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnFill = (Button) findViewById(R.id.btn_fill);
        btnDel = (Button) findViewById(R.id.btn_delete);
        btnAggregation = (Button) findViewById(R.id.btn_aggregation);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnCleanAll = (Button) findViewById(R.id.btn_clean_all);
        btnQuery = (Button) findViewById(R.id.btn_query);
        btnThread = (Button) findViewById(R.id.btn_thread);


        btnSave.setOnClickListener(this);
        btnFill.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnAggregation.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnCleanAll.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnThread.setOnClickListener(this);
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
            case R.id.btn_aggregation:
                aggregation();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_clean_all:
                clearDatabase();
                break;
            case R.id.btn_query:
                query();
                break;
            case R.id.btn_thread:
                thread();
                break;
        }
    }

    /**
     * 多线程操作
     */
    private void thread() {
        Thread thread1 = new Thread(new QueryRunnable());
        Thread thread2 = new Thread(new UpdateRunnable());
        thread2.start();
        thread1.start();
    }

    /**
     * 同步查
     */
    private void query() {
        list = realm.where(EventEntity.class)
                .equalTo("city", "北京1")
                .equalTo("sub_title", "副标题5")
                .findAll()
                .sort("id", Sort.DESCENDING);

        StringBuilder sb = new StringBuilder();
        for (EventEntity entity : list) {
            sb.append(entity.toString());
            sb.append("\n");
        }
        tvText.setText(sb.toString());

    }

    /**
     * 修改一条
     */
    private void update() {

        final EventEntity entity = realm.where(EventEntity.class).equalTo("id", 10090).findFirst();
        if (entity != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    entity.setCity("天津");
                }
            });
        } else {
            Toast.makeText(this, "没有更新", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 聚合查询
     */
    private void aggregation() {
        RealmResults<EventEntity> results = realm.where(EventEntity.class).findAll();
        long sum = results.sum("id").longValue();
        long min = results.min("id").longValue();
        long max = results.max("id").longValue();
        double average = results.average("id");
        long matches = results.size();


        // int city_sum = results.("city").intValue();

        Log.e("aggregation", "sum：" + sum);
        Log.e("aggregation", "min：" + min);
        Log.e("aggregation", "max：" + max);
        Log.e("aggregation", "average：" + average);
        Log.e("aggregation", "matches：" + matches);

        int city_sum = realm.where(EventEntity.class).equalTo("city", "北京1").findAll().size();
        Log.e("aggregation", "city_sum：" + city_sum);

    }

    private void delete() {
        final RealmResults<EventEntity> event = realm.where(EventEntity.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //删除所有数据
                event.deleteAllFromRealm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void save() {

        final long sss = System.currentTimeMillis();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.insertOrUpdate(mData);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                long eee = System.currentTimeMillis();
                Log.e("lee", "save---耗时：" + (eee - sss));
                Toast.makeText(MainActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(MainActivity.this, "插入失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    long ss;
    RealmResults<EventEntity> list;

    private void fill() {
        /**
         *异步查询
         */
        ss = System.currentTimeMillis();
        list = realm.where(EventEntity.class).between("id", 10000, 11000).findAllAsync();
        list.addChangeListener(callback);
    }

    /**
     * 这里调了多次
     */
    private RealmChangeListener callback = new RealmChangeListener<RealmResults<EventEntity>>() {
        @Override
        public void onChange(RealmResults<EventEntity> element) {
            long ee = System.currentTimeMillis();
            Log.e("lee", "fill耗时：" + (ee - ss));
            tvText.setText("耗时：" + (ee - ss));
            StringBuilder sb = new StringBuilder();
            for (EventEntity entity : list) {
                sb.append(entity.toString());
                sb.append("\n");
            }
            tvText.setText(sb.toString());
            list.removeAllChangeListeners();
        }
    };

    /**
     * 清空数据库
     *
     * @return
     */
    public boolean clearDatabase() {
        try {
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }
}
