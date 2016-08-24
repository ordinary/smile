package com.smile.spider.actor.result.processor;

import org.springframework.stereotype.Service;

/**
 * Created by zhutao on 15/9/11.
 */
@Service
public class Job51Processor implements IProcessor {
    @Override
    public String getType() {
        return "51job";
    }

    @Override
    public void dealWith(String db, String document) {



    }
}
