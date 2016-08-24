package com.smile.spider.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhutao on 15/9/14.
 */
public class InterUtils {

    private final static Map<String, Long> RELENT_TIME = new ConcurrentHashMap<String, Long>();

    public static void sleep(String taskId, long sleep) {
        if (RELENT_TIME.get(taskId) != null && System.currentTimeMillis() - RELENT_TIME.get(taskId) < sleep) {
            try {
//                Thread.sleep(System.currentTimeMillis() - RELENT_TIME.get(taskId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        RELENT_TIME.put(taskId, System.currentTimeMillis());
    }
}
