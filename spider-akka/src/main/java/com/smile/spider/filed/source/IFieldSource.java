package com.smile.spider.filed.source;


import com.smile.spider.enums.FieldSourceType;
import com.smile.spider.xml.FieldSource;

/**
 * Created by zhutao on 14/12/27.
 */
public interface IFieldSource {

    FieldSourceType getFiledType();

    String source(FieldSource fieldSource, Object global, Object element, String sourceUrl);


}
