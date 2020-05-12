package com.sun.common.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Sun
 */
@Data
@NoArgsConstructor
public class Typing {

    private K middle;
    private K begin;
    private K end;
    private TypingEnum type;
    private Double extremum;
    private LocalDateTime time;

    public Typing(K middle, K begin, K end, TypingEnum type) {
        this.middle = middle;
        this.begin = begin;
        this.end = end;
        this.type = type;
        if (TypingEnum.TOP.equals(type)) {
            extremum = middle.getHigh();
        } else {
            extremum = middle.getLow();
        }
    }

    @Override
    public String toString() {
        return type.getDesc() + " :" + middle.getTimeBegin() + " - " + middle.getTimeEnd();
    }

    public LocalDateTime getTime() {
        if (middle != null) {
            return middle.getTimeBegin();
        }
        return null;
    }
}