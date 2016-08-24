package com.smile.spider.actor.field;

import akka.actor.PoisonPill;
import akka.actor.UntypedActor;

import com.smile.spider.dao.SpiderTableDao;
import com.smile.spider.message.field.JsonElementMessage;
import com.smile.spider.utils.AppContext;
import com.smile.spider.utils.SpiderConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 15/9/11.
 */
public class JsonElementActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof JsonElementMessage) {
            JsonElementMessage message = (JsonElementMessage) o;
            Map<String, Object> result = new HashMap<String, Object>();
            boolean require = false;
            result.put(SpiderConstants.FETCH_TIME, System.currentTimeMillis());
            SpiderTableDao spiderTableDao = AppContext.getBean(SpiderTableDao.class);
            if (result.get(SpiderConstants.ID_NAME) != null) {
                if (spiderTableDao.selectOne(message.getJob().getDb(), (String) result.get(SpiderConstants.ID_NAME)) == null) {
                    if (!require) {
                        spiderTableDao.saveData(message.getJob().getDb(), result);
                    }
                }
            } else {
                if (!require) {
                    spiderTableDao.saveData(message.getJob().getDb(), result);
                }
            }

        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }

    }
}
