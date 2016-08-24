package com.smile.spider.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhutao on 15/3/9.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "page")
public class Page {

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "listParser")
    private String listParser;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getListParser() {
        return listParser;
    }

    public void setListParser(String listParser) {
        this.listParser = listParser;
    }
}
