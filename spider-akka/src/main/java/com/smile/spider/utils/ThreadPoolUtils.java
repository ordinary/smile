package com.smile.spider.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhutao on 15/3/14.
 */
public class ThreadPoolUtils {

    private final static int TREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 20 + 200;

    private final static ExecutorService executorService = Executors.newFixedThreadPool(TREAD_POOL_SIZE);


    public static void runDb(Runnable runnable) {
        executorService.execute(runnable);
    }
}
