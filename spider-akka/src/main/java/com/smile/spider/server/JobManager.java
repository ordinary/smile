package com.smile.spider.server;

import com.smile.spider.xml.Job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhutao on 15/8/5.
 */
public class JobManager {

    private final static Map<String, Job> JOB_MAP = new ConcurrentHashMap<String, Job>();

    public static void addActor(String key, Job job) {
        JOB_MAP.put(key, job);
    }

    public static Job selectJob(String key) {
        return JOB_MAP.get(key);
    }

}
