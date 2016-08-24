package com.smile.spider.message.page;

import com.smile.spider.xml.Job;
import com.smile.spider.xml.Page;
import com.smile.spider.xml.TaskHttp;

/**
 * Created by zhutao on 15/8/5.
 */
public class PageMessage {

    private String taskId;

    private String url;

    private long sleep;

    private Job job;

    private Page page;

    private TaskHttp taskHttp;

    private int currentPage;

    private int tryTime = 0;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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


    public int getTryTime() {
        return tryTime;
    }

    public void setTryTime(int tryTime) {
        this.tryTime = tryTime;
    }
}
