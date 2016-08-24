package com.smile.spider.filed.parser;

import com.smile.spider.enums.FieldParserType;
import com.smile.spider.xml.FieldParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhutao on 15/9/6.
 */
@Service
public class RegexParser implements IFieldParser {
    @Override
    public FieldParserType getFiledType() {
        return FieldParserType.REGEX;
    }

    @Override
    public String parse(FieldParser fieldParser, String source) {
        if (StringUtils.isNotEmpty(source)) {
            Matcher matcher = Pattern.compile(fieldParser.getMatch()).matcher(source);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }
}
