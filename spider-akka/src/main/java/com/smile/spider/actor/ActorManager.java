package com.smile.spider.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.smile.spider.utils.SpiderConstants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhutao on 2015/7/27.
 */
public class ActorManager {

    private static ActorSystem actorSystem;

    static {
        actorSystem = ActorSystem.create(SpiderConstants.SYSTEM_NAME);
    }

    public static ActorSystem getActorSystem() {
        return actorSystem;
    }

    private final static Map<String, ActorRef> ACTOR_REF_MAP = new ConcurrentHashMap<String, ActorRef>();

    public static void addActor(String key, ActorRef actorRef) {
        ACTOR_REF_MAP.put(key, actorRef);
    }


    public static ActorRef selectActor(String key, Class<? extends UntypedActor> actorClass) {
        if (ACTOR_REF_MAP.get(key) == null) {
            ActorRef fieldActor = ActorManager.getActorSystem().actorOf(Props.create(actorClass));
            ActorManager.addActor(key, fieldActor);
        }
        return ACTOR_REF_MAP.get(key);
    }


}
