package com.smile.spider.filed.parser;

import com.smile.spider.enums.FieldParserType;
import com.smile.spider.filed.parser.interfaces.IInterfaceFieldParser;
import com.smile.spider.filed.parser.interfaces.InterfaceFieldParserFactory;
import com.smile.spider.xml.FieldParser;
import org.springframework.stereotype.Service;

/**
 * Created by zhutao on 15/9/17.
 */
@Service
public class InterfaceFieldParser implements IFieldParser {
    @Override
    public FieldParserType getFiledType() {
        return FieldParserType.INTERFACE;
    }

    @Override
    public String parse(FieldParser fieldParser, String source) {
        IInterfaceFieldParser interfaceFieldParser = InterfaceFieldParserFactory.getFieldParser(fieldParser.getInterfaceName());
        return interfaceFieldParser.parse(source);
    }
}
