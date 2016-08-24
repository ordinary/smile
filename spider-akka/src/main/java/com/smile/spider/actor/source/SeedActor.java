package com.smile.spider.actor.source;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.actor.ActorManager;
import com.smile.spider.actor.page.PageActor;
import com.smile.spider.actor.page.SingleActor;
import com.smile.spider.message.page.PageMessage;
import com.smile.spider.message.page.SingleMessage;
import com.smile.spider.message.source.SeedMessage;
import com.smile.spider.utils.SpiderConstants;

/**
 * Created by zhutao on 2015/7/27.
 */
public class SeedActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof SeedMessage) {
            SeedMessage seedMessage = (SeedMessage) o;
            if (seedMessage.getPage() != null) {
                PageMessage pageMessage = new PageMessage();
                pageMessage.setUrl(seedMessage.getSeed());
                pageMessage.setTaskHttp(seedMessage.getTaskHttp());
                pageMessage.setJob(seedMessage.getJob());
                pageMessage.setPage(seedMessage.getPage());
                pageMessage.setSleep(seedMessage.getSleep());
                pageMessage.setTaskId(seedMessage.getTaskId());
                pageMessage.setCurrentPage(1);
                ActorManager.selectActor(SpiderConstants.PAGE_ACTOR, PageActor.class).tell(pageMessage, ActorRef.noSender());
            } else {
                SingleMessage singleMessage = new SingleMessage();
                singleMessage.setUrl(seedMessage.getSeed());
                singleMessage.setTaskHttp(seedMessage.getTaskHttp());
                singleMessage.setJob(seedMessage.getJob());
                singleMessage.setTaskId(seedMessage.getTaskId());
                ActorManager.selectActor(SpiderConstants.SINGLE_ACTOR, SingleActor.class).tell(singleMessage, ActorRef.noSender());
            }
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }

    }

}
