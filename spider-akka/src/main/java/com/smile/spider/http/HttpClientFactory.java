package com.smile.spider.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientFactory {

    /**
     * 设置连接的超时时间
     */
    private static int connectionTimeout = 10000 * 60;
    /**
     * 读取数据的超时时间
     */
    private static int soTimeout = 10000 * 60;
    /**
     * 设置每个路由最大连接数
     */
    private static int defaultMaxPerRoute = 100;
    /**
     * 设置最大连接数
     */
    private static int maxTotal = 500;

    private static final CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setMaxTotal(maxTotal);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(soTimeout).build();

        httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config).build();
    }

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
//        return (CloseableHttpClient) HttpClientPoolManager.getHttpClient();
    }
}