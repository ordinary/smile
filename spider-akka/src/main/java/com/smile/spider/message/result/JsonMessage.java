package com.smile.spider.message.result;

import com.smile.spider.message.page.PageMessage;
import com.smile.spider.xml.Job;

/**
 * Created by zhutao on 2015/8/7.
 */
public class JsonMessage {

    private String source;

    private String url;

    private Job job;

    private String listParser;

    private PageMessage pageMessage;


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }


    public String getListParser() {
        return listParser;
    }

    public void setListParser(String listParser) {
        this.listParser = listParser;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageMessage getPageMessage() {
        return pageMessage;
    }

    public void setPageMessage(PageMessage pageMessage) {
        this.pageMessage = pageMessage;
    }
}
