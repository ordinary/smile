package com.smile.spider.actor.page;

import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.actor.parser.HttpClientActor;
import com.smile.spider.message.page.PageMessage;
import com.smile.spider.message.parser.HttpClientMessage;
import com.smile.spider.xml.Page;

/**
 * Created by zhutao on 2015/7/27.
 */
public class PageActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof PageMessage) {
            PageMessage message = (PageMessage) o;
            HttpClientMessage clientMessage = new HttpClientMessage();
            clientMessage.setTaskHttp(message.getTaskHttp());
            clientMessage.setJob(message.getJob());
            clientMessage.setListParser(message.getPage().getListParser());
            clientMessage.setUrl(parserUrl(message.getUrl(), message.getPage(), message.getCurrentPage()));
            clientMessage.setPageMessage(message);
            clientMessage.setTaskId(message.getTaskId());
            HttpClientActor.dealwith(clientMessage);
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }
    }


    private String parserUrl(String url, Page page, int currentPage) {
        if (page.getType().equalsIgnoreCase("regex")) {
            return String.format(url, currentPage);
        }
        return "";
    }
}
