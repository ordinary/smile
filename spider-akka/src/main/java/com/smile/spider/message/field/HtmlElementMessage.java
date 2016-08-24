package com.smile.spider.message.field;

import com.smile.spider.xml.Job;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by zhutao on 15/9/11.
 */
public class HtmlElementMessage {

    private Job job;

    private Document document;

    private Element element;

    private String url;


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
