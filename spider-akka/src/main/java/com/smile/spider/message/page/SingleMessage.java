package com.smile.spider.message.page;

import com.smile.spider.xml.Job;
import com.smile.spider.xml.TaskHttp;

/**
 * Created by zhutao on 15/8/5.
 */
public class SingleMessage {

    private String taskId;

    private String url;

    private Job job;


    private TaskHttp taskHttp;

    private String listParser;


    public String getListParser() {
        return listParser;
    }

    public void setListParser(String listParser) {
        this.listParser = listParser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public TaskHttp getTaskHttp() {
        return taskHttp;
    }

    public void setTaskHttp(TaskHttp taskHttp) {
        this.taskHttp = taskHttp;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
