package com.sun.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Sun
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class K {

    private Double open;
    private Double close;

    private Double high;
    private Double low;

    private LocalDateTime timeBegin;
    private LocalDateTime timeEnd;
    private Integer index;

    /**
     * 成交量
     */
    private Double volume;
    /**
     * 成交额
     */
    private Double amount;

    public boolean contains(K k) {
        if (this.high >= k.getHigh() && this.low <= k.getLow()) {
            return true;
        }
        return false;
    }

    public boolean higher(K k) {
        if (this.high >= k.getHigh() && this.low >= k.getLow()) {
            return true;
        }
        return false;
    }

    public boolean highest(K k1, K k2) {
        if (higher(k1) && higher(k2)) {
            return true;
        }
        return false;
    }


    public boolean lower(K k) {
        if (this.high <= k.getHigh() && this.low <= k.getLow()) {
            return true;
        }
        return false;
    }

    public boolean lowest(K k1, K k2) {
        if (lower(k1) && lower(k2)) {
            return true;
        }
        return false;
    }
}