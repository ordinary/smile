package com.smile.spider;

/**
 * Created by zhutao on 15/9/23.
 */

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class httpclient {
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

    private static CloseableHttpClient httpClient;

    /**
     * heepclient 抓取页面
     * jroup 解析页面内容
     *
     * @param args
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setMaxTotal(maxTotal);
        HttpHost httpHost = new HttpHost("221.208.194.108", 80, "https");
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setProxy(httpHost)
                .setSocketTimeout(soTimeout).build();
        httpClient = HttpClientBuilder.create().disableAutomaticRetries()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config).build();
        System.out.println(getHTML("http://www.chinahr.com/jobs/10000/1/", "202.38.95.66", 1080));
    }


    /**
     * cookie方法的getHTMl() 设置cookie策略,防止cookie rejected问题,拒绝写入cookie     --重载,3参数:url, hostName, port
     */
    public static String getHTML(String url, String hostName, int port) throws URISyntaxException, IOException {
        //采用用户自定义的cookie策略
        HttpHost proxy = new HttpHost(hostName, port);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CookieSpecProvider cookieSpecProvider = new CookieSpecProvider() {
            public CookieSpec create(HttpContext context) {
                return new BrowserCompatSpec() {
                    @Override
                    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                        //Oh, I am easy...
                    }
                };
            }
        };
        Registry<CookieSpecProvider> r = RegistryBuilder
                .<CookieSpecProvider>create()
                .register("easy", cookieSpecProvider)
                .build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec("easy")
                .setSocketTimeout(50000) //socket超时
                .setConnectTimeout(50000) //connect超时
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieSpecRegistry(r)
                .setRoutePlanner(routePlanner).disableAutomaticRetries()
                .build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String html = "null"; //用于验证是否正常取到html
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            html = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("----Connection timeout----");
        }
        return html;
    }


}


