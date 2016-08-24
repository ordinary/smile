package com.smile.spider.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhutao on 15/8/3.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "source")
public class FieldSource {

    @XmlAttribute(name = "global")
    private String global;

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "attr")
    private String attr;

    @XmlAttribute(name = "tagParser")
    private String tagParser;

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getTagParser() {
        return tagParser;
    }

    public void setTagParser(String tagParser) {
        this.tagParser = tagParser;
    }
}
