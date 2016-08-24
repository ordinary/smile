package com.smile.spider.actor.source;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.actor.ActorManager;
import com.smile.spider.dao.SpiderTableDao;
import com.smile.spider.dao.SpiderTableRecordDao;
import com.smile.spider.message.source.DBMessage;
import com.smile.spider.message.source.SeedMessage;
import com.smile.spider.utils.AppContext;
import com.smile.spider.utils.InterUtils;
import com.smile.spider.utils.SpiderConstants;
import com.smile.spider.utils.ThreadPoolUtils;
import com.smile.spider.xml.TaskSource;
import com.mongodb.BasicDBObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zhutao on 2015/7/27.
 */
public class DBActor extends UntypedActor {

    private static Map<String, AtomicBoolean> isStart = new HashMap<String, AtomicBoolean>();

    private final static Map<String, Long> RELENT_TIME = new ConcurrentHashMap<String, Long>();

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof DBMessage) {
            final DBMessage dbMessage = (DBMessage) o;
            AtomicBoolean flag = isStart.get(dbMessage.getJob().getDb());
            if (flag == null) {
                flag = new AtomicBoolean(false);
                isStart.put(dbMessage.getJob().getDb(), flag);
            }
            if (flag.compareAndSet(false, true)) {
                ThreadPoolUtils.runDb(new Runnable() {
                    @Override
                    public void run() {
                        newMessage(dbMessage);
                    }
                });
//                ThreadPoolUtils.runDb(new Runnable() {
//                    @Override
//                    public void run() {
//                        olderMessage(dbMessage);
//                    }
//                });
            }
        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }
    }


    /**
     * 从一个星期前向前查找消息
     *
     * @param dbMessage
     */
    private void newMessage(DBMessage dbMessage) {
        TaskSource taskSource = dbMessage.getSource();
        SpiderTableDao spiderTableDao = AppContext.getBean(SpiderTableDao.class);
        long lastTime = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        do {
            List<BasicDBObject> result = spiderTableDao.selectData(taskSource.getDb(), lastTime, 50);
            if (result.size() <= 0) {
                try {
                    //若没有数据，睡30秒在跑
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            for (BasicDBObject dbObject : result) {
                InterUtils.sleep(dbMessage.getTaskId(), dbMessage.getSleep());
                dealwith(dbMessage, taskSource, dbObject);
                long time = dbObject.getLong(SpiderConstants.FETCH_TIME);
                if (time > lastTime) {
                    lastTime = time;
                }
            }
        } while (true);
    }

    /**
     * 从最老向最前查找消息
     *
     * @param dbMessage
     */
    private void olderMessage(DBMessage dbMessage) {
        TaskSource taskSource = dbMessage.getSource();
        SpiderTableDao spiderTableDao = AppContext.getBean(SpiderTableDao.class);
        SpiderTableRecordDao spiderTableRecordDao = AppContext.getBean(SpiderTableRecordDao.class);
        Long lastTime = spiderTableRecordDao.getLastId(taskSource.getDb());
        if (lastTime == null) {
            lastTime = 0L;
        }
        do {
            List<BasicDBObject> result = spiderTableDao.selectData(taskSource.getDb(), lastTime, 50);
            if (result.size() <= 0) {
                try {
                    //若没有数据，睡30秒在跑
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            for (BasicDBObject dbObject : result) {
                if (spiderTableDao.selectOne(dbMessage.getJob().getDb(), (String) dbObject.get(SpiderConstants.ID_NAME)) != null) {
                    continue;
                }
                InterUtils.sleep(dbMessage.getTaskId(), dbMessage.getSleep());
                dealwith(dbMessage, taskSource, dbObject);
                long time = dbObject.getLong(SpiderConstants.FETCH_TIME);
                if (time > lastTime) {
                    lastTime = time;
                }
            }
            spiderTableRecordDao.saveLastId(taskSource.getDb(), lastTime);
        } while (true);
    }

    private void dealwith(DBMessage dbMessage, TaskSource taskSource, BasicDBObject dbObject) {
        String url = parser(taskSource, dbMessage, dbObject);
        SeedMessage seedMessage = new SeedMessage();
        seedMessage.setSeed(url);
        seedMessage.setJob(dbMessage.getJob());
        seedMessage.setPage(dbMessage.getPage());
        seedMessage.setTaskHttp(dbMessage.getTaskHttp());
        seedMessage.setTaskId(dbMessage.getTaskId());
        seedMessage.setSleep(dbMessage.getSleep());
        seedMessage.setListParser(dbMessage.getListParser());
        ActorManager.selectActor(SpiderConstants.SEED_ACTOR, SeedActor.class).tell(seedMessage, ActorRef.noSender());
    }


    private String parser(TaskSource taskSource, DBMessage message, BasicDBObject dbObject) {
        String fieldValue = dbObject.getString(taskSource.getField());
        if (taskSource.getParser().getType().equalsIgnoreCase("field")) {
            return fieldValue;
        } else if (taskSource.getParser().getType().equalsIgnoreCase("match")) {
            return String.format(taskSource.getParser().getUrl(), fieldValue);
        }
        return "";
    }

}
