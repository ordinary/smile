package com.smile.spider.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by zhutao on 15/9/16.
 */
public class Htmlunit {

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {

        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        try {
            HttpGet httpget = new HttpGet("http://so.dajie.com/job/search?jobsearch=1&ct=1396511076185&keyword=&statType=button&city=&_CSRFToken=");
            CloseableHttpResponse response1 = httpclient.execute(httpget);
            try {
                HttpEntity entity = response1.getEntity();
                System.out.println("Login form get: " + response1.getStatusLine());
                EntityUtils.consume(entity);
                System.out.println("Initial set of cookies:");
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());
                    }
                }
            } finally {
                response1.close();
            }

            HttpUriRequest login = RequestBuilder.get()
                    .setUri(new URI("http://so.dajie.com/job/ajax/search/filter?jobsearch=0&pagereferer=blank&ajax=1&page=1&order=0&from=user"))
                    .build();
            CloseableHttpResponse response2 = httpclient.execute(login);
            try {
                HttpEntity entity = response2.getEntity();
                System.out.println("Login form get: " + response2.getStatusLine());
                System.out.println(EntityUtils.toString(entity));
                EntityUtils.consume(entity);

                System.out.println("Post logon cookies:");
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());
                    }
                }

            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
    }

    private void test() throws InterruptedException {
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_11);
        driver.setJavascriptEnabled(true);
        driver.get("http://so.dajie.com/job/search");
        Thread.sleep(60000);
        System.out.println("before:   ++++++   " + driver.getPageSource());
        WebElement submit = driver.findElement(By.className("next"));
        submit.click();

        System.out.println("before:   ++++++   " + driver.getPageSource());
    }
}
