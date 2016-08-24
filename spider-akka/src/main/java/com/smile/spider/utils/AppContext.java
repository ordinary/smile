package com.smile.spider.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by zhutao on 16/4/18.
 */

@Component
public class AppContext implements ApplicationContextAware{

    @Autowired
    private static ApplicationContext applicationContext;


    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext=applicationContext;
    }
}
