package com.smile.spider.filed.parser.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhutao on 15/9/2.
 */
@Service
public class DefaultInterfaceParser implements IInterfaceFieldParser {
    @Override
    public String getFiledType() {
        return "source";
    }

    @Override
    public String parse(String source) {
        Date sysTime = new Date();/*当前系统时间*/
        Long time = (long) 60 * 60 * 24 * 1000;/*一天*/
        Long publishTime = null;/*发布时间*/
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        if (StringUtils.isNotBlank(source)) {
            if (source.contains("今天") || source.contains("小时") || source.contains("分钟")) {
                source = dateFormat.format(sysTime);
            } else if (source.contains("昨天")) {
                publishTime = sysTime.getTime() - time;
                source = dateFormat.format(publishTime);
            } else if (source.contains("前天")) {
                publishTime = sysTime.getTime() - time * 2;
                source = dateFormat.format(publishTime);
            } else if (source.matches("[0-9]+天前")) {
                Matcher matcher = Pattern.compile("([0-9]+)前天").matcher(source);
                if (matcher.find()) {
                    int i = Integer.parseInt(matcher.group(1));
                    publishTime = sysTime.getTime() - time * i;
                    source = dateFormat.format(publishTime);
                }

            }
        }
        return source;
    }
}
