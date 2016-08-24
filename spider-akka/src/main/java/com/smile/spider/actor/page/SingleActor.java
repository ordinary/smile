package com.smile.spider.actor.page;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.actor.ActorManager;
import com.smile.spider.actor.parser.HttpClientActor;
import com.smile.spider.message.page.SingleMessage;
import com.smile.spider.message.parser.HttpClientMessage;
import com.smile.spider.utils.SpiderConstants;

/**
 * Created by zhutao on 2015/7/27.
 */
public class SingleActor extends UntypedActor {


    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof SingleMessage) {
            SingleMessage message = (SingleMessage) o;
            HttpClientMessage clientMessage = new HttpClientMessage();
            clientMessage.setTaskHttp(message.getTaskHttp());
            clientMessage.setJob(message.getJob());
            clientMessage.setListParser(message.getListParser());
            clientMessage.setUrl(message.getUrl());
            clientMessage.setTaskId(message.getTaskId());
            ActorManager.selectActor(SpiderConstants.HTTP_CLIENT_ACTOR, HttpClientActor.class).tell(clientMessage, ActorRef.noSender());
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }

    }
}
