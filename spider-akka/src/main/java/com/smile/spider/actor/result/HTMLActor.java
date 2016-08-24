package com.smile.spider.actor.result;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.actor.ActorManager;
import com.smile.spider.actor.field.HtmlElementActor;
import com.smile.spider.actor.page.PageActor;
import com.smile.spider.message.field.HtmlElementMessage;
import com.smile.spider.message.page.PageMessage;
import com.smile.spider.message.result.HtmlMessage;
import com.smile.spider.utils.FieldUtils;
import com.smile.spider.utils.InterUtils;
import com.smile.spider.utils.SpiderConstants;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by zhutao on 2015/7/27.
 */
public class HTMLActor extends UntypedActor {


    @Override
    public void onReceive(Object o) throws Exception {
//        System.out.println(" HTML :" + new Date());
        if (o instanceof HtmlMessage) {
            HtmlMessage message = (HtmlMessage) o;
            Document document = Jsoup.parse(message.getSource());
            if (StringUtils.isNotEmpty(message.getListParser())) {
                Elements elements = document.select(message.getListParser());
                if (elements.size() > 0) {
                    for (Element element : elements) {
                        HtmlElementMessage htmlElementMessage = new HtmlElementMessage();
                        htmlElementMessage.setJob(message.getJob());
                        htmlElementMessage.setDocument(document);
                        htmlElementMessage.setElement(element);
                        htmlElementMessage.setUrl(message.getUrl());
                        boolean isExist = FieldUtils.dealWith(htmlElementMessage);
//                            if (isExist) {
//                                return;
//                            }
                    }
                    PageMessage pageMessage = message.getPageMessage();
                    if (pageMessage != null) {
                        pageMessage.setTryTime(0);
                        pageMessage.setCurrentPage(pageMessage.getCurrentPage() + 1);
                        InterUtils.sleep(pageMessage.getTaskId(), pageMessage.getSleep());
                        ActorManager.selectActor(SpiderConstants.PAGE_ACTOR, PageActor.class).tell(pageMessage, ActorRef.noSender());
                    }
                } else {
                    PageMessage pageMessage = message.getPageMessage();
                    System.out.println("not document:" + message.getSource());
                    if (pageMessage != null) {
//                        if (pageMessage.getTryTime() < 4) {
                        try {
                            Thread.sleep(10 * 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (pageMessage != null) {
                            pageMessage.setCurrentPage(pageMessage.getCurrentPage());
                            pageMessage.setTryTime(pageMessage.getTryTime() + 1);
                            ActorManager.selectActor(SpiderConstants.PAGE_ACTOR, PageActor.class).tell(pageMessage, ActorRef.noSender());
                        }
//                        }
                    }
                }
            } else {
                HtmlElementMessage htmlElementMessage = new HtmlElementMessage();
                htmlElementMessage.setJob(message.getJob());
                htmlElementMessage.setDocument(document);
                htmlElementMessage.setElement(document);
                htmlElementMessage.setUrl(message.getUrl());
                ActorManager.selectActor(SpiderConstants.HTML_ELEMENT_ACTOR, HtmlElementActor.class).tell(htmlElementMessage, ActorRef.noSender());
            }
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }
    }
}
