package com.sun.data.web;

import com.sun.common.bean.K;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sun
 */
public interface GetData {

    /**
     * 查询从指定日期到最新
     *
     * @param code
     * @param dateStart
     * @param dateEnd
     * @return
     */
    List<K> getDayData(String code, LocalDateTime dateStart, LocalDateTime dateEnd);
}
