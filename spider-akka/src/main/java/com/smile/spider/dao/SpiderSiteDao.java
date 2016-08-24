package com.smile.spider.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Map;

/**
 * Created by zhutao on 15/9/25.
 */
@Repository
public class SpiderSiteDao {


    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String collectionName = "httpList";

    public List selectData() {
        return mongoTemplate.find(query(where("type").is("HTTP")), Map.class, collectionName);
    }

}
