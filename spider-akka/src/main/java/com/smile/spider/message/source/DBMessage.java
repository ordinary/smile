package com.smile.spider.message.source;

import com.smile.spider.xml.Job;
import com.smile.spider.xml.Page;
import com.smile.spider.xml.TaskHttp;
import com.smile.spider.xml.TaskSource;

/**
 * Created by zhutao on 15/8/5.
 */
public class DBMessage {

    private String taskId;

    private boolean isList;

    private String listParser;

    private long sleep;

    private TaskSource source;

    private TaskHttp taskHttp;

    private Job job;

    private String responseType;

    private Page page;

    public boolean isList() {
        return isList;
    }

    public void setIsList(boolean isList) {
        this.isList = isList;
    }

    public String getListParser() {
        return listParser;
    }

    public void setListParser(String listParser) {
        this.listParser = listParser;
    }

    public TaskSource getSource() {
        return source;
    }

    public void setSource(TaskSource source) {
        this.source = source;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public long getSleep() {
        return sleep;
    }

    public void setSleep(long sleep) {
        this.sleep = sleep;
    }

    public TaskHttp getTaskHttp() {
        return taskHttp;
    }

    public void setTaskHttp(TaskHttp taskHttp) {
        this.taskHttp = taskHttp;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
