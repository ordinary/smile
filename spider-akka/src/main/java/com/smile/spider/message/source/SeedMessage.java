package com.smile.spider.message.source;

import com.smile.spider.xml.Job;
import com.smile.spider.xml.Page;
import com.smile.spider.xml.TaskHttp;

/**
 * Created by zhutao on 15/8/5.
 */
public class SeedMessage {

    private String taskId;

    private String seed;

    private Job job;

    private Page page;


    private String listParser;

    private long sleep;

    private TaskHttp taskHttp;


    public String getListParser() {
        return listParser;
    }

    public void setListParser(String listParser) {
        this.listParser = listParser;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
