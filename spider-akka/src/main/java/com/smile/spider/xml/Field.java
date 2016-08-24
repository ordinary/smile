package com.smile.spider.xml;

import javax.xml.bind.annotation.*;

/**
 * Created by zhutao on 14/12/24.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "field")
public class Field {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "require")
    private boolean require;

    @XmlElement(name = "source")
    private FieldSource source;

    @XmlElement(name = "parser")
    private FieldParser parser;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public FieldSource getSource() {
        return source;
    }

    public void setSource(FieldSource source) {
        this.source = source;
    }

    public FieldParser getParser() {
        return parser;
    }

    public void setParser(FieldParser parser) {
        this.parser = parser;
    }

}
