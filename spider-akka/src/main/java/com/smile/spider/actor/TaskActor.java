package com.smile.spider.actor;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.actor.source.DBActor;
import com.smile.spider.actor.source.SeedActor;
import com.smile.spider.message.TaskMessage;
import com.smile.spider.message.source.DBMessage;
import com.smile.spider.message.source.SeedMessage;
import com.smile.spider.utils.SpiderConstants;
import com.smile.spider.xml.Task;

/**
 * Created by zhutao on 2015/7/27.
 */
public class TaskActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof TaskMessage) {
            TaskMessage taskMessage = (TaskMessage) o;
            Task task = taskMessage.getTask();
            if (task.getTaskSeed() != null) {
                for (String url : task.getTaskSeed().getUrls()) {
                    SeedMessage seedMessage = new SeedMessage();
                    seedMessage.setSeed(url);
                    seedMessage.setTaskHttp(task.getTaskHttp());
                    seedMessage.setJob(taskMessage.getJob());
                    seedMessage.setPage(task.getPage());
                    seedMessage.setTaskId(task.getId());
                    seedMessage.setSleep(task.getSleep());
                    ActorManager.selectActor(SpiderConstants.SEED_ACTOR, SeedActor.class).tell(seedMessage, ActorRef.noSender());
                }
            } else if (task.getTaskSource() != null) {
                DBMessage dbMessage = new DBMessage();
                dbMessage.setSource(task.getTaskSource());
                dbMessage.setTaskHttp(task.getTaskHttp());
                dbMessage.setJob(taskMessage.getJob());
                dbMessage.setPage(task.getPage());
                dbMessage.setTaskId(task.getId());
                dbMessage.setSleep(task.getSleep());
                ActorManager.selectActor(SpiderConstants.DB_ACTOR, DBActor.class).tell(dbMessage, ActorRef.noSender());
            } else {

            }
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }
    }
}
