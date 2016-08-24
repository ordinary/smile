package com.smile.spider.http;

/**
 * Created by zhutao on 15/9/24.
 */
public class HttpInfo {

    /**
     * 主机
     */
    private String host;

    /**
     * 端口号
     */
    private int port;

    /**
     * 运行速度
     */
    private int speedTime;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSpeedTime() {
        return speedTime;
    }

    public void setSpeedTime(int speedTime) {
        this.speedTime = speedTime;
    }
}
