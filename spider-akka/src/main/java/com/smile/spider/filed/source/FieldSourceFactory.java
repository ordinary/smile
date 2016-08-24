package com.smile.spider.filed.source;

import com.smile.spider.enums.FieldSourceType;
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
public class FieldSourceFactory implements ApplicationContextAware {


    private static Map<FieldSourceType, IFieldSource> fieldSourceParsers = new HashMap<FieldSourceType, IFieldSource>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IFieldSource> fieldParserMap = applicationContext.getBeansOfType(IFieldSource.class);
        for (Map.Entry<String, IFieldSource> fieldParserEntry : fieldParserMap.entrySet()) {
            fieldSourceParsers.put(fieldParserEntry.getValue().getFiledType(), fieldParserEntry.getValue());
        }
    }

    public static IFieldSource getFieldSource(FieldSourceType fieldSourceType) {
        return fieldSourceParsers.get(fieldSourceType);
    }
}
