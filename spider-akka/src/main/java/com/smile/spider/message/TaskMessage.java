package com.smile.spider.message;

import com.smile.spider.xml.Job;
import com.smile.spider.xml.Task;

/**
 * Created by zhutao on 15/8/5.
 */
public class TaskMessage {

    private Task task;

    private Job job;

    private String webName;


    public TaskMessage(Task task, String webName) {
        this.task = task;
        this.webName = webName;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
