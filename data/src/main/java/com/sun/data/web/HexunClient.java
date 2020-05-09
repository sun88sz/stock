package com.sun.data.web;

import com.sun.common.bean.K;
import com.sun.data.parse.HexunParse;
import com.sun.data.parse.ParseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<K> getDayData(String code, LocalDateTime dateStart, LocalDateTime dateEnd) {
        String prefix;
        if (code.startsWith("6")) {
            prefix = "sse";
        } else {
            prefix = "szse";
        }
        String index = "";
        String date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        if (dateEnd == null && dateStart != null) {
            index = "10000";
            date = formatter.format(dateStart);
        } else if (dateEnd != null && dateStart == null) {
            index = "-10000";
            date = formatter.format(dateEnd);
        } else {
            return null;
        }

        // http://webstock.quote.hermes.hexun.com/a/kline?code=sse601066&start=20200101000000&number=100&type=5
        String str = MessageFormat.format("http://webstock.quote.hermes.hexun.com/a/kline?code={0}{1}&start={2}&number={3}&type=5",
                prefix, code, date, index);

        String body = GetUtil.sendGet(str);

        List<K> parse = hexunParse.parse(body);
        System.out.println(parse);

        return parse;
    }
}
