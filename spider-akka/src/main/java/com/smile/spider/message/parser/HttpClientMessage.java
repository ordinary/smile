package com.smile.spider.message.parser;

import com.smile.spider.message.page.PageMessage;
import com.smile.spider.xml.Job;
import com.smile.spider.xml.TaskHttp;
import com.smile.spider.xml.TaskSource;

/**
 * Created by zhutao on 15/8/19.
 */
public class HttpClientMessage {

    private String taskId;

    private long sleep;

    private String url;

    private TaskSource source;

    private Job job;

    private TaskHttp taskHttp;

    private PageMessage pageMessage;

    private String listParser;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TaskHttp getTaskHttp() {
        return taskHttp;
    }

    public void setTaskHttp(TaskHttp taskHttp) {
        this.taskHttp = taskHttp;
    }

    public PageMessage getPageMessage() {
        return pageMessage;
    }

    public void setPageMessage(PageMessage pageMessage) {
        this.pageMessage = pageMessage;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getSleep() {
        return sleep;
    }

    public void setSleep(long sleep) {
        this.sleep = sleep;
    }
}
