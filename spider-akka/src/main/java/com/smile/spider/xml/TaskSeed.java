package com.smile.spider.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by zhutao on 15/3/9.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "seeds")
public class TaskSeed {

    @XmlElement(name = "url")
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}
