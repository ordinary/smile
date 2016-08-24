package com.smile.spider.filed.source;

import com.smile.spider.enums.FieldSourceType;
import com.smile.spider.xml.FieldSource;
import org.springframework.stereotype.Service;

/**
 * Created by zhutao on 15/8/10.
 */
@Service
public class UrlFieldSource implements IFieldSource {

    @Override
    public FieldSourceType getFiledType() {
        return FieldSourceType.URL;
    }

    @Override
    public String source(FieldSource fieldSource, Object global, Object element, String sourceUrl) {
        return sourceUrl;
    }


}
