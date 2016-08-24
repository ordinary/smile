package com.smile.spider.actor.result.processor;

/**
 * Created by zhutao on 15/9/11.
 */
public interface IProcessor {

    /**
     * 处理器类型
     *
     * @return
     */
    String getType();

    /**
     * 处理数据
     *
     * @param db
     * @param document
     */
    void dealWith(String db, String document);
}
