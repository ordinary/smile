package com.smile.spider.dao;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.data.mongodb.core.query.Update;
/**
 * 记录抓取表格历史数据
 * Created by zhutao on 15/3/14.
 */
@Repository
public class SpiderTableRecordDao {

    @Value("${db.working.dbName}")
    private String dbName;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String SPIDER_TABLE = "spiderTable";

    public Long getLastId(String url) {
        BasicDBObject dbObject = getDBObject(url);
        if (dbObject != null) {
            return getDBObject(url).getLong("LastId", 0);
        } else {
            return null;
        }
    }


    private BasicDBObject getDBObject(String db) {
        return mongoTemplate.findOne(query(where("dbName").is(db)), BasicDBObject.class, SPIDER_TABLE);
    }

    public void saveLastId(String db, Long lastId) {
        mongoTemplate.upsert(query(where("dbName").is(db)), new Update().set("LastId", lastId).set("dbName", db), SPIDER_TABLE);
    }


}
