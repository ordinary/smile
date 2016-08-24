package com.smile.spider.actor;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.message.TaskMessage;
import com.smile.spider.utils.SpiderConstants;
import com.smile.spider.xml.Task;
import com.smile.spider.xml.WebSite;

import java.util.Map;

/**
 * Created by zhutao on 15/8/9.
 */
public class CookieActor extends UntypedActor {


    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof WebSite) {
            WebSite webSite = (WebSite) o;
            Map<String, String> cookies = null;
            //有返回的task
            ActorManager.selectActor(SpiderConstants.COOKIE_ACTOR, TaskActor.class).tell(webSite, getSelf());
            for (Task task : webSite.getTasks()) {
                task.getTaskHttp().setCookies(cookies);
                TaskMessage taskMessage = new TaskMessage(task, webSite.getName());
                ActorManager.selectActor(SpiderConstants.TASK_ACTOR, TaskActor.class).tell(taskMessage, ActorRef.noSender());
            }
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }
    }
}
