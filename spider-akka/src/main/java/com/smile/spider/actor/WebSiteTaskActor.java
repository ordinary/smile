package com.smile.spider.actor;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.message.TaskMessage;
import com.smile.spider.utils.SpiderConstants;
import com.smile.spider.xml.Job;
import com.smile.spider.xml.Task;
import com.smile.spider.xml.WebSite;

/**
 * Created by zhutao on 15/8/8.
 */
public class WebSiteTaskActor extends UntypedActor {



    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof WebSite) {
            WebSite webSite = (WebSite) o;
            if (webSite.getCookie() != null) {
                //有返回的task
                ActorManager.selectActor(SpiderConstants.COOKIE_ACTOR, TaskActor.class).tell(webSite, getSelf());
            } else {
                for (Task task : webSite.getTasks()) {
                    TaskMessage taskMessage = new TaskMessage(task, webSite.getName());
                    for (Job job : webSite.getJobs()) {
                        if (job.getId().equals(taskMessage.getTask().getJob())) {
                            taskMessage.setJob(job);
                            break;
                        }
                    }
                    ActorManager.selectActor(SpiderConstants.TASK_ACTOR, TaskActor.class).tell(taskMessage, ActorRef.noSender());
                }
            }
        } else if (o instanceof TaskMessage) {
            ActorManager.selectActor(SpiderConstants.TASK_ACTOR, TaskActor.class).tell(o, ActorRef.noSender());
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }
    }
}
