package com.smile.spider.filed.source;

import com.smile.spider.enums.FieldSourceType;
import com.smile.spider.xml.FieldSource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * Created by zhutao on 15/8/10.
 */
@Service
public class TextFieldSource implements IFieldSource {

    @Override
    public FieldSourceType getFiledType() {
        return FieldSourceType.TEXT;
    }

    @Override
    public String source(FieldSource fieldSource, Object all, Object unit, String sourceUrl) {
        Element global = (Element) all;
        Element elementUnit = (Element) unit;
        if (StringUtils.isNotEmpty(fieldSource.getGlobal())) {
            Elements elements = global.select(fieldSource.getTagParser());
            for (Element element : elements) {
                return element.text();
            }
        } else {
            Elements elements = elementUnit.select(fieldSource.getTagParser());
            for (Element element : elements) {
                return element.text();
            }
        }
        return null;
    }

}
