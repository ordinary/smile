package com.smile.spider.utils;


import com.smile.spider.dao.SpiderTableDao;
import com.smile.spider.enums.FieldParserType;
import com.smile.spider.enums.FieldSourceType;
import com.smile.spider.filed.parser.FieldParserFactory;
import com.smile.spider.filed.parser.IFieldParser;
import com.smile.spider.filed.source.FieldSourceFactory;
import com.smile.spider.filed.source.IFieldSource;
import com.smile.spider.message.field.HtmlElementMessage;
import com.smile.spider.message.field.JsonElementMessage;
import com.smile.spider.xml.Field;
import com.smile.spider.xml.FieldParser;
import com.smile.spider.xml.FieldSource;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 15/9/14.
 */
public class FieldUtils {

    public static boolean dealWith(HtmlElementMessage message) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            boolean require = false;
            for (Field field : message.getJob().getFields()) {
                FieldSource fieldSource = field.getSource();
                FieldParser fieldParser = field.getParser();
                IFieldSource fieldSourceParser = FieldSourceFactory.getFieldSource(FieldSourceType.getFieldType(field.getSource().getType()));
                String source = fieldSourceParser.source(fieldSource, message.getDocument(), message.getElement(), message.getUrl());
                Object fieldValue = null;
                if (fieldParser == null) {
                    fieldValue = source;
                } else {
                    IFieldParser parser = FieldParserFactory.getFieldParser(FieldParserType.getFieldParserType(fieldParser.getType()));
                    fieldValue = parser.parse(fieldParser, source);
                }
                if ((fieldValue == null || StringUtils.isBlank((String) fieldValue)) && field.isRequire()) {
                    require = true;
                    break;
                }
                if (fieldValue != null) {
                    result.put(field.getName(), fieldValue);
                }
            }
            result.put(SpiderConstants.FETCH_TIME, System.currentTimeMillis());
            SpiderTableDao spiderTableDao = AppContext.getBean(SpiderTableDao.class);
            if (result.get(SpiderConstants.ID_NAME) != null) {
                if (spiderTableDao.selectOne(message.getJob().getDb(), (String) result.get(SpiderConstants.ID_NAME)) == null) {
                    if (!require) {
                        System.out.println(" date:" + new Date() + "db:" + message.getJob().getDb() + " url:" + message.getUrl() + " save");
                        spiderTableDao.saveData(message.getJob().getDb(), result);
                    } else {
                        System.out.println(" date:" + new Date() + "db:" + message.getJob().getDb() + " url:" + message.getUrl());
                    }

                } else {
                    System.out.println("date:" + new Date() + " db:" + message.getJob().getDb() + " jId:" + result.get(SpiderConstants.ID_NAME) + " is exist.");
                    return true;
                }
            } else {
                if (!require) {
                    System.out.println(" date:" + new Date() + "db:" + message.getJob().getDb() + " url:" + message.getUrl() + " save");
                    spiderTableDao.saveData(message.getJob().getDb(), result);
                } else {
                    System.out.println(" date:" + new Date() + "db:" + message.getJob().getDb() + " url:" + message.getUrl() + " is empty.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean dealWith(JsonElementMessage message) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            boolean require = false;
            for (Field field : message.getJob().getFields()) {
                FieldSource fieldSource = field.getSource();
                FieldParser fieldParser = field.getParser();
                IFieldSource fieldSourceParser = FieldSourceFactory.getFieldSource(FieldSourceType.getFieldType(field.getSource().getType()));
                String source = fieldSourceParser.source(fieldSource, message.getAll(), message.getField(), message.getUrl());
                Object fieldValue = null;
                if (fieldParser == null) {
                    fieldValue = source;
                } else {
                    IFieldParser parser = FieldParserFactory.getFieldParser(FieldParserType.getFieldParserType(fieldParser.getType()));
                    fieldValue = parser.parse(fieldParser, source);
                }
                if ((fieldValue == null || StringUtils.isBlank((String) fieldValue)) && field.isRequire()) {
                    require = true;
                    break;
                }
                if (fieldValue != null) {
                    result.put(field.getName(), fieldValue);
                }
            }
            result.put(SpiderConstants.FETCH_TIME, System.currentTimeMillis());
            SpiderTableDao spiderTableDao = AppContext.getBean(SpiderTableDao.class);
            if (result.get(SpiderConstants.ID_NAME) != null) {
                System.out.println("job:" + message.getJob());
                if (spiderTableDao.selectOne(message.getJob().getDb(), (String) result.get(SpiderConstants.ID_NAME)) == null) {
                    if (!require) {
                        System.out.println(" date:" + new Date() + "db:" + message.getJob().getDb() + " url:" + message.getUrl() + " save");
                        spiderTableDao.saveData(message.getJob().getDb(), result);
                    } else {
                        System.out.println(" date:" + new Date() + "db:" + message.getJob().getDb() + " url:" + message.getUrl());
                    }

                } else {
                    System.out.println("date:" + new Date() + " db:" + message.getJob().getDb() + " jId:" + result.get(SpiderConstants.ID_NAME));
                    return true;
                }
            } else {
                if (!require) {
                    System.out.println(" date:" + new Date() + "db:" + message.getJob().getDb() + " url:" + message.getUrl() + " save");
                    spiderTableDao.saveData(message.getJob().getDb(), result);
                } else {
                    System.out.println(" date:" + new Date() + "db:" + message.getJob().getDb() + " url:" + message.getUrl() + " is empty.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
