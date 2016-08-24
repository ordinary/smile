package com.smile.spider.filed.parser;

import com.smile.spider.enums.FieldParserType;
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
public class FieldParserFactory implements ApplicationContextAware {


    private static Map<FieldParserType, IFieldParser> fieldParsers = new HashMap<FieldParserType, IFieldParser>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IFieldParser> fieldParserMap = applicationContext.getBeansOfType(IFieldParser.class);
        for (Map.Entry<String, IFieldParser> fieldParserEntry : fieldParserMap.entrySet()) {
            fieldParsers.put(fieldParserEntry.getValue().getFiledType(), fieldParserEntry.getValue());
        }
    }

    public static IFieldParser getFieldParser(FieldParserType fieldParserType) {
        return fieldParsers.get(fieldParserType);
    }
}
