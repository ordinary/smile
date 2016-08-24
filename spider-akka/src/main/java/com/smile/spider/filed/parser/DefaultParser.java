package com.smile.spider.filed.parser;

import com.smile.spider.enums.FieldParserType;
import com.smile.spider.xml.FieldParser;
import org.springframework.stereotype.Service;

/**
 * Created by zhutao on 15/9/2.
 */
@Service
public class DefaultParser implements IFieldParser {
    @Override
    public FieldParserType getFiledType() {
        return FieldParserType.SOURCE;
    }

    @Override
    public String parse(FieldParser fieldParser, String source) {
        return source;
    }
}
