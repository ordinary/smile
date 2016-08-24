package com.smile.spider.actor.parser;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.smile.spider.actor.ActorManager;
import com.smile.spider.actor.page.PageActor;
import com.smile.spider.actor.result.HTMLActor;
import com.smile.spider.actor.result.JsonActor;
import com.smile.spider.actor.result.processor.IProcessor;
import com.smile.spider.actor.result.processor.ProcessorFactory;
import com.smile.spider.http.HttpClientUtils;
import com.smile.spider.message.page.PageMessage;
import com.smile.spider.message.parser.HttpClientMessage;
import com.smile.spider.message.result.HtmlMessage;
import com.smile.spider.message.result.JsonMessage;
import com.smile.spider.utils.InterUtils;
import com.smile.spider.utils.SpiderConstants;
import com.smile.spider.xml.Header;
import com.smile.spider.xml.Param;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 15/8/19.
 */
public class HttpClientActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println("HttpClient:" + new Date());
        if (o instanceof HttpClientMessage) {
            HttpClientMessage message = (HttpClientMessage) o;
            InterUtils.sleep(message.getTaskId(), message.getSleep());
            try {
                System.out.println("HttpClient begin source:" + new Date());
                String source = execute(message);
                System.out.println("HttpClient end source:" + new Date());
                if (message.getTaskHttp().getResponseType().equalsIgnoreCase("json")) {
                    JsonMessage jsonMessage = new JsonMessage();
                    jsonMessage.setJob(message.getJob());
                    jsonMessage.setSource(source);
                    jsonMessage.setUrl(message.getUrl().trim());
                    jsonMessage.setListParser(message.getListParser());
                    jsonMessage.setPageMessage(message.getPageMessage());
                    ActorManager.selectActor(SpiderConstants.JSON_ACTOR, JsonActor.class).tell(jsonMessage, ActorRef.noSender());
                } else if (message.getTaskHttp().getResponseType().equalsIgnoreCase("html")) {
                    HtmlMessage htmlMessage = new HtmlMessage();
                    htmlMessage.setJob(message.getJob());
                    htmlMessage.setSource(source);
                    htmlMessage.setUrl(message.getUrl().trim());
                    htmlMessage.setListParser(message.getListParser());
                    htmlMessage.setPageMessage(message.getPageMessage());
                    ActorManager.selectActor(SpiderConstants.THML_ACTOR, HTMLActor.class).tell(htmlMessage, ActorRef.noSender());
                } else if (message.getTaskHttp().getResponseType().equalsIgnoreCase("interface")) {
                    IProcessor processor = ProcessorFactory.getProcessor(message.getJob().getService());
                    processor.dealWith(message.getJob().getDb(), source);
                }
            } catch (Exception e) {
                PageMessage pageMessage = message.getPageMessage();
                InterUtils.sleep(pageMessage.getTaskId(), pageMessage.getSleep());
                ActorManager.selectActor(SpiderConstants.PAGE_ACTOR, PageActor.class).tell(pageMessage, ActorRef.noSender());
            }
        }
    }


    public static void dealwith(HttpClientMessage message) {
        InterUtils.sleep(message.getTaskId(), message.getSleep());
        try {
//            System.out.println("HttpClient begin source:" + new Date());
            String source = execute(message);
//            System.out.println("HttpClient end source:" + new Date());
            if (message.getTaskHttp().getResponseType().equalsIgnoreCase("json")) {
                JsonMessage jsonMessage = new JsonMessage();
                jsonMessage.setJob(message.getJob());
                jsonMessage.setSource(source);
                jsonMessage.setUrl(message.getUrl().trim());
                jsonMessage.setListParser(message.getListParser());
                jsonMessage.setPageMessage(message.getPageMessage());
                ActorManager.selectActor(SpiderConstants.JSON_ACTOR, JsonActor.class).tell(jsonMessage, ActorRef.noSender());
            } else if (message.getTaskHttp().getResponseType().equalsIgnoreCase("html")) {
                HtmlMessage htmlMessage = new HtmlMessage();
                htmlMessage.setJob(message.getJob());
                htmlMessage.setSource(source);
                htmlMessage.setUrl(message.getUrl().trim());
                htmlMessage.setListParser(message.getListParser());
                htmlMessage.setPageMessage(message.getPageMessage());
                ActorManager.selectActor(SpiderConstants.THML_ACTOR, HTMLActor.class).tell(htmlMessage, ActorRef.noSender());
            } else if (message.getTaskHttp().getResponseType().equalsIgnoreCase("interface")) {
                IProcessor processor = ProcessorFactory.getProcessor(message.getJob().getService());
                processor.dealWith(message.getJob().getDb(), source);
            }
        } catch (Exception e) {
            PageMessage pageMessage = message.getPageMessage();
            InterUtils.sleep(pageMessage.getTaskId(), pageMessage.getSleep());
            ActorManager.selectActor(SpiderConstants.PAGE_ACTOR, PageActor.class).tell(pageMessage, ActorRef.noSender());
        }
    }

    private static String execute(HttpClientMessage message) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        if (message.getTaskHttp().getParam() != null) {
            for (Param param : message.getTaskHttp().getParam()) {
                params.put(param.getName(), param.getValue());
            }
        }
        Map<String, String> headers = new HashMap<String, String>();
        if (message.getTaskHttp().getHeader() != null) {
            for (Header header : message.getTaskHttp().getHeader()) {
                headers.put(header.getName(), header.getValue());
            }
        }
        if (StringUtils.isNotEmpty(message.getTaskHttp().getMethod())) {
            return execute(message, params, headers);
        }
        return "";
    }

    private static String execute(HttpClientMessage message, Map<String, String> params, Map<String, String> headers) {
        try {
            if (message.getTaskHttp().getMethod().equalsIgnoreCase("post")) {
                return HttpClientUtils.postString(message.getUrl().trim(), params, headers, message.getTaskHttp().getCharset());
            } else if (message.getTaskHttp().getMethod().equalsIgnoreCase("get")) {
                return HttpClientUtils.getString(message.getUrl().trim(), params, headers, message.getTaskHttp().getCharset());
            } else {
                return "";
            }
        } catch (Exception e) {
            return execute(message, params, headers);
        }
    }


    public static void main(String[] args) throws IOException {
        String url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=000000,00&district=000000&funtype=0000&industrytype=31&issuedate=9&providesalary=99&keywordtype=1&curr_page=3&lang=c&stype=2&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=01&companysize=99&lonlat=0,0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=-1";

        Map<String, String> headers = new HashMap<String, String>();
//        headers.put( "Host","search.51job.com");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
        System.out.println(HttpClientUtils.getString(url, new HashMap<String, String>(), headers, "gb2312"));
    }
}
