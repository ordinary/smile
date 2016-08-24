package com.smile.spider.xml;


import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by zhutao on 14/12/24.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "website")
public class WebSite {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "job")
    private List<Job> jobs;

    @XmlElement(name = "task")
    private List<Task> tasks;

    @XmlElement(name = "cookie")
    private CookieTask cookie;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public CookieTask getCookie() {
        return cookie;
    }

    public void setCookie(CookieTask cookie) {
        this.cookie = cookie;
    }
}
