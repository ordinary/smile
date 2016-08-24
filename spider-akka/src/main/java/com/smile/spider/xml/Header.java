package com.smile.spider.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhutao on 15/7/30.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "header")
public class Header {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "value")
    private String value;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
