package com.smile.spider.filed.parser.interfaces;


/**
 * Created by zhutao on 14/12/27.
 */
public interface IInterfaceFieldParser {

    String getFiledType();

    String parse(String source);

}
