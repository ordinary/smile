package com.smile.spider.message.field;

import com.alibaba.fastjson.JSONObject;
import com.smile.spider.xml.Job;

/**
 * Created by zhutao on 15/9/11.
 */
public class JsonElementMessage {

    private Job job;

    private JSONObject field;

    private JSONObject all;

    private String url;


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JSONObject getField() {
        return field;
    }

    public void setField(JSONObject field) {
        this.field = field;
    }

    public JSONObject getAll() {
        return all;
    }

    public void setAll(JSONObject all) {
        this.all = all;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
