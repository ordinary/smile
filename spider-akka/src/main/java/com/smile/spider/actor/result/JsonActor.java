package com.smile.spider.actor.result;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smile.spider.actor.ActorManager;
import com.smile.spider.actor.page.PageActor;
import com.smile.spider.message.field.JsonElementMessage;
import com.smile.spider.message.page.PageMessage;
import com.smile.spider.message.result.JsonMessage;
import com.smile.spider.utils.FieldUtils;
import com.smile.spider.utils.InterUtils;
import com.smile.spider.utils.SpiderConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhutao on 2015/7/27.
 */
public class JsonActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof JsonMessage) {
            JsonMessage message = (JsonMessage) o;
            PageMessage pageMessage = message.getPageMessage();
            if (StringUtils.isNotEmpty(message.getListParser())) {
                JSONObject jsonObject = JSON.parseObject(message.getSource());
                String[] parsers = message.getListParser().split(",");
                JSON json = jsonObject;
                for (String parser : parsers) {
                    if (json instanceof JSONObject) {
                        json = (JSON) ((JSONObject) json).get(parser);
                        if (json == null) {
                            return;
                        }
                    }
                }
                if (json != null && json instanceof JSONArray) {
                    for (Object o1 : ((JSONArray) json)) {
                        JsonElementMessage jsonMessage = new JsonElementMessage();
                        jsonMessage.setJob(message.getJob());
                        jsonMessage.setUrl(message.getUrl());
                        jsonMessage.setAll(jsonObject);
                        jsonMessage.setField((JSONObject) o1);
                        FieldUtils.dealWith(jsonMessage);
                    }
                    if (pageMessage != null) {
                        pageMessage.setTryTime(0);
                        pageMessage.setCurrentPage(pageMessage.getCurrentPage() + 1);
                        InterUtils.sleep(pageMessage.getTaskId(), pageMessage.getSleep());
                        ActorManager.selectActor(SpiderConstants.PAGE_ACTOR, PageActor.class).tell(pageMessage, ActorRef.noSender());
                    }
                } else {
                    if (pageMessage != null) {
                        if (pageMessage.getTryTime() < 4) {
                            try {
                                Thread.sleep(10 * 60 * 1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (pageMessage != null) {
                                pageMessage.setCurrentPage(pageMessage.getCurrentPage());
                                pageMessage.setTryTime(pageMessage.getTryTime() + 1);
                                ActorManager.selectActor(SpiderConstants.PAGE_ACTOR, PageActor.class).tell(pageMessage, ActorRef.noSender());
                            }
                        }
                    }
                }
            }
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }
    }

}
