package com.smile.spider.filed.parser.interfaces;

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
public class InterfaceFieldParserFactory implements ApplicationContextAware {


    private static Map<String, IInterfaceFieldParser> fieldParsers = new HashMap<String, IInterfaceFieldParser>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IInterfaceFieldParser> fieldParserMap = applicationContext.getBeansOfType(IInterfaceFieldParser.class);
        for (Map.Entry<String, IInterfaceFieldParser> fieldParserEntry : fieldParserMap.entrySet()) {
            fieldParsers.put(fieldParserEntry.getValue().getFiledType(), fieldParserEntry.getValue());
        }
    }

    public static IInterfaceFieldParser getFieldParser(String  interfaceName) {
        return fieldParsers.get(interfaceName);
    }
}
