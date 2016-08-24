package com.smile.spider.filed.parser;


import com.smile.spider.enums.FieldParserType;
import com.smile.spider.xml.FieldParser;

/**
 * Created by zhutao on 14/12/27.
 */
public interface IFieldParser {

    FieldParserType getFiledType();

    String parse(FieldParser fieldParser, String source);

}
