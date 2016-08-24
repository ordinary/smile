package com.smile.spider.http;

import com.smile.spider.dao.SpiderSiteDao;
import com.mongodb.BasicDBObject;
import com.smile.spider.utils.AppContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by zhutao on 15/9/23.
 */
public class HttpClientPoolManager {

    /**
     * 设置连接的超时时间
     */
    private final static int connectionTimeout = 10000 * 60;
    /**
     * 读取数据的超时时间
     */
    private final static int soTimeout = 10000 * 60;
    /**
     * 设置每个路由最大连接数
     */
    private final static int defaultMaxPerRoute = 100;
    /**
     * 设置最大连接数
     */
    private final static int maxTotal = 500;

    private final static Queue<HttpClient> queue = new ConcurrentLinkedDeque<>();


    static {
        SpiderSiteDao spiderSiteDao = AppContext.getBean(SpiderSiteDao.class);
        List<BasicDBObject> https = spiderSiteDao.selectData();
        queue.add(initHttpClient());
        for (BasicDBObject dbObject : https) {
            if (StringUtils.isNotEmpty(dbObject.getString("speed"))) {
                String speed = dbObject.getString("speed");
                double speedValue = Double.valueOf(speed.substring(0, speed.indexOf("秒")));
                if (speedValue < 4) {
                    queue.add(initHttpClient(dbObject.getString("ipAddress"), Integer.valueOf(dbObject.getString("port"))));
                }
            }

        }
    }

    public static HttpClient getHttpClient() {
        HttpClient httpClient = queue.poll();
        queue.add(httpClient);
        return httpClient;
    }


    public static HttpClient initHttpClient(String hostName, int port) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setMaxTotal(maxTotal);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(soTimeout).build();
        HttpHost proxy = new HttpHost(hostName, port);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return HttpClientBuilder.create()
                .setConnectionManager(connectionManager).setRoutePlanner(routePlanner)
                .setDefaultRequestConfig(config).build();
    }

    public static HttpClient initHttpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setMaxTotal(maxTotal);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(soTimeout).build();
        return HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
//                .disableAutomaticRetries()
                .setDefaultRequestConfig(config)
                .build();
    }

    public boolean check(HttpClient client) {
        HttpGet httpGet = new HttpGet("https://www.baidu.com/");
        try {
            HttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) { //状态码200: OK
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
