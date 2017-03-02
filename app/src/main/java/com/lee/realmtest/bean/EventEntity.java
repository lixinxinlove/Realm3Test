package com.lee.realmtest.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by android on 2017/3/2.
 */
public class EventEntity extends RealmObject {

    @PrimaryKey
    private long id;
    // 活动ID
    private String eid;
    // 活动ID
    private String event_id;
    // 活动标题
    private String title;
    // 子标题
    private String sub_title;

    private String detail_url;

    private String city;

    public EventEntity(){}

    public EventEntity(long id, String eid, String event_id, String title, String sub_title, String detail_url, String city) {
        this.id = id;
        this.eid = eid;
        this.event_id = event_id;
        this.title = title;
        this.sub_title = sub_title;
        this.detail_url = detail_url;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
