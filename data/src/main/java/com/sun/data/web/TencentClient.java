package com.sun.data.web;

import com.sun.common.bean.K;
import com.sun.data.parse.ParseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sun
 */
@Service
public class TencentClient implements GetData {

    @Autowired
    @Qualifier("tencentParse")
    private ParseData parseData;


    @Override
    public List<K> getDayData(String code, LocalDateTime dateStart, LocalDateTime dateEnd) {
        String url = "http://data.gtimg.cn/flashdata/hushen/daily/19/sh601066.js?visitDstTime=1";
        String body = GetUtil.sendGet(url);
        List<K> parse = parseData.parse(body);
        System.out.println(parse);
        return parse;
    }
}
