package com.smile.spider.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhutao on 15/8/3.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "parser")
public class FieldParser {

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "match")
    private String match;

    @XmlAttribute(name = "interfaceName")
    private String interfaceName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }


    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
