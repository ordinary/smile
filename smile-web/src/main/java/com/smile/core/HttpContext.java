package com.smile.core;

import org.apache.commons.codec.Charsets;
import org.springframework.http.HttpMethod;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by zhutao on 2016/9/3.
 */
public class HttpContext {

    private HttpMethod httpMethod;

    private int timeout;

    private boolean isProxy;

    private String url;

    private String charset = Charsets.UTF_8.name();

    private Map<String, String> headers;

    private Map<String, String> params;

    /**
     * POST 传输json串时
     */
    private String body;


    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isProxy() {
        return isProxy;
    }

    public void setProxy(boolean proxy) {
        isProxy = proxy;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
