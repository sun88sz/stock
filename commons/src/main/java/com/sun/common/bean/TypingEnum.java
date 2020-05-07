package com.sun.common.bean;

import lombok.Getter;

/**
 * @author Sun
 */

@Getter
public enum TypingEnum {

    /**
     * 顶 / 底
     */
    TOP (1,"顶"),
    BOTTOM(0,"底");

    TypingEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
        private String desc;

}
