package com.smile.spider.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhutao on 15/7/30.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "source")
public class TaskSourceParser {

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "url")
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
