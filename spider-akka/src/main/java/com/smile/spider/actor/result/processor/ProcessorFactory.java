package com.smile.spider.actor.result.processor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 14/12/27.
 */

@Service
public class ProcessorFactory implements ApplicationContextAware {


    private static Map<String, IProcessor> processors = new HashMap<String, IProcessor>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IProcessor> processorMap = applicationContext.getBeansOfType(IProcessor.class);
        for (Map.Entry<String, IProcessor> processorEntry : processorMap.entrySet()) {
            processors.put(processorEntry.getValue().getType(), processorEntry.getValue());
        }
    }

    public static IProcessor getProcessor(String type) {
        return processors.get(type);
    }
}
