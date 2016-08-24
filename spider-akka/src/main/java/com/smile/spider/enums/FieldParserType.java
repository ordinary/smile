package com.smile.spider.enums;

/**
 * Created by zhutao on 14/12/27.
 */
public enum FieldParserType {

    SOURCE("source"),

    INTERFACE("interface"),

    REGEX("regex");

    private String name;

    FieldParserType(String name) {
        this.name = name;
    }

    public static FieldParserType getFieldParserType(String name) {
        for (FieldParserType fieldParserType : FieldParserType.values()) {
            if (fieldParserType.name.equals(name)) {
                return fieldParserType;
            }
        }
        return SOURCE;
    }

}
