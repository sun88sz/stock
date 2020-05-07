package com.sun.data.web;

import com.sun.common.bean.K;
import com.sun.data.parse.HexunParse;
import com.sun.data.parse.ParseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sun
 */
@Service
public class HexunClient implements GetData {

    @Autowired
    @Qualifier("hexunParse")
    private ParseData hexunParse;


    @Override
    public List<K> getData(String code) {

        String x;
        if (code.startsWith("6")) {
            x = "sse";
        } else {
            x = "szse";
        }

        String body = GetUtil.sendGet("http://webstock.quote.hermes.hexun.com/a/kline?code=" + x + code + "&start=20200101000000&number=100&type=5");

        List<K> parse = hexunParse.parse(body);
        System.out.println(parse);

        return parse;
    }
}
