package com.smile.spider.xml;

import javax.xml.bind.annotation.*;

/**
 * Created by zhutao on 15/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "task")
public class Task {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "sleep")
    private long sleep;

    @XmlAttribute(name = "job")
    private String job;

    @XmlElement(name = "http")
    private TaskHttp taskHttp;

    @XmlElement(name = "source")
    private TaskSource taskSource;

    @XmlElement(name = "seeds")
    private TaskSeed taskSeed;

    @XmlElement(name = "page")
    private Page page;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public TaskSource getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(TaskSource taskSource) {
        this.taskSource = taskSource;
    }

    public TaskSeed getTaskSeed() {
        return taskSeed;
    }

    public void setTaskSeed(TaskSeed taskSeed) {
        this.taskSeed = taskSeed;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
