package com.smile.spider.filed.source;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smile.spider.enums.FieldSourceType;
import com.smile.spider.xml.FieldSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by zhutao on 15/9/16.
 */
@Service
public class JsonFieldSource implements IFieldSource {

    @Override
    public FieldSourceType getFiledType() {
        return FieldSourceType.JSON;
    }

    @Override
    public String source(FieldSource fieldSource, Object global, Object element, String sourceUrl) {
        if (StringUtils.isNotEmpty(fieldSource.getGlobal())) {
            if (StringUtils.isEmpty(fieldSource.getTagParser())) {
                return ((JSONObject) global).getString(fieldSource.getAttr());
            }
            String[] parsers = fieldSource.getTagParser().split(",");
            JSON json = (JSON) global;
            for (String parser : parsers) {
                if (json instanceof JSONObject) {
                    json = (JSON) ((JSONObject) json).get(parser);
                    if (json == null) {
                        break;
                    }
                }
            }
            if (json == null) {
                return null;
            }
            return ((JSONObject) json).getString(fieldSource.getAttr());
        } else {
            if (StringUtils.isEmpty(fieldSource.getTagParser())) {
                return ((JSONObject) element).getString(fieldSource.getAttr());
            }
            String[] parsers = fieldSource.getTagParser().split(",");
            JSON json = (JSON) element;
            for (String parser : parsers) {
                if (json instanceof JSONObject) {
                    json = (JSON) ((JSONObject) json).get(parser);
                    if (json == null) {
                        break;
                    }
                }
            }
            if (json == null) {
                return null;
            }
            return ((JSONObject) json).getString(fieldSource.getAttr());
        }
    }
}
