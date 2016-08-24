package com.smile.spider.enums;

/**
 * Created by zhutao on 14/12/27.
 */
public enum FieldSourceType {

    ATTR("attr"),

    TEXT("text"),

    URL("url"),

    JSON("json");

    private String name;

    FieldSourceType(String name) {
        this.name = name;
    }

    public static FieldSourceType getFieldType(String name) {
        for (FieldSourceType fieldSourceType : FieldSourceType.values()) {
            if (fieldSourceType.name.equals(name)) {
                return fieldSourceType;
            }
        }
        return null;
    }

}
