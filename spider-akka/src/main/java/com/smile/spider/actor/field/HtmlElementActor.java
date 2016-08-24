package com.smile.spider.actor.field;

import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.smile.spider.dao.SpiderTableDao;
import com.smile.spider.enums.FieldParserType;
import com.smile.spider.enums.FieldSourceType;
import com.smile.spider.filed.parser.FieldParserFactory;
import com.smile.spider.filed.parser.IFieldParser;
import com.smile.spider.filed.source.FieldSourceFactory;
import com.smile.spider.filed.source.IFieldSource;
import com.smile.spider.message.field.HtmlElementMessage;
import com.smile.spider.utils.AppContext;
import com.smile.spider.utils.SpiderConstants;
import com.smile.spider.xml.Field;
import com.smile.spider.xml.FieldParser;
import com.smile.spider.xml.FieldSource;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 15/9/11.
 */
public class HtmlElementActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
//        System.out.println("HtmlElement:" + new Date());
        if (o instanceof HtmlElementMessage) {
            HtmlElementMessage message = (HtmlElementMessage) o;
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
                        spiderTableDao.saveData(message.getJob().getDb(), result);
                    } else {
//                        System.out.println(" date:" + new Date() + " db:" + message.getJob().getDb() + " is empty.");
                    }
                }
            } else {
                if (!require) {
                    spiderTableDao.saveData(message.getJob().getDb(), result);
                } else {
//                    System.out.println(" date:" + new Date() + " db:" + message.getJob().getDb() + " is empty.");
                }
            }

        } else {
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }

    }
}
