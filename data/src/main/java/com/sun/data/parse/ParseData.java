package com.sun.data.parse;

import com.sun.common.bean.K;

import java.util.List;

/**
 * @author Sun
 */
public interface ParseData {

    /**
     * 解析数据
     *
     * @param data
     * @return
     */
    List<K> parse(String data);
}
