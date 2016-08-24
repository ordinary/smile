package com.smile.spider.xml;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Created by zhutao on 2015/8/7.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "http")
public class TaskHttp {

    @XmlAttribute(name = "method")
    private String method;

    @XmlAttribute(name = "responseType")
    private String responseType;

    @XmlAttribute(name = "charset")
    private String charset;

    @XmlAttribute(name = "timeout")
    private Integer timeout;

    @XmlElement(name = "header")
    private List<Header> header;

    @XmlElement(name = "param")
    private List<Param> param;

    private Map<String, String> cookies;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }


    public List<Header> getHeader() {
        return header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }

    public List<Param> getParam() {
        return param;
    }

    public void setParam(List<Param> param) {
        this.param = param;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}
