package com.smile.spider.dao;


import com.smile.spider.utils.SpiderConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by zhutao on 15/3/14.
 */
@Repository
public class SpiderTableDao {


    @Value("${db.working.dbName}")
    private String dbName;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveData(String db, Map<String, Object> data) {
        DBObject saveDBObject = new BasicDBObject();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            saveDBObject.put(entry.getKey(), entry.getValue());
        }
        mongoTemplate.save(saveDBObject, db);
    }

    public List selectData(String db, long lastDid, int size) {
        return mongoTemplate.find(query(where(SpiderConstants.FETCH_TIME).gt(lastDid)).with(new Sort(Sort.Direction.ASC, SpiderConstants.FETCH_TIME)).limit(size), Map.class, db);
    }

    public BasicDBObject selectOne(String db, String field, Object value) {
        return mongoTemplate.findOne(query(where(field).is(value)), BasicDBObject.class, db);
    }

    public BasicDBObject selectOne(String db, String id) {
        return mongoTemplate.findOne(query(where(SpiderConstants.ID_NAME).is(id)), BasicDBObject.class, db);
    }
}
