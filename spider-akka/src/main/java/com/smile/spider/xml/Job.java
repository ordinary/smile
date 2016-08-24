package com.smile.spider.xml;


import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by zhutao on 14/12/24.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "job")
public class Job {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "db")
    private String db;

    @XmlAttribute(name = "service")
    private String service;


    @XmlElement(name = "field")
    private List<Field> fields;


    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
