package com.smile.spider.xml;

import javax.xml.bind.annotation.*;

/**
 * Created by zhutao on 15/7/30.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "source")
public class TaskSource {

    @XmlAttribute(name = "db")
    private String db;

    @XmlAttribute(name = "field")
    private String field;

    @XmlElement(name = "parser")
    private TaskSourceParser parser;


    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    public TaskSourceParser getParser() {
        return parser;
    }

    public void setParser(TaskSourceParser parser) {
        this.parser = parser;
    }
}
