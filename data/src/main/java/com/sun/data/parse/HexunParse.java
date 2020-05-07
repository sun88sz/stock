package com.sun.data.parse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sun.common.bean.K;
import com.sun.data.parse.ParseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Sun
 */
@Service
public class HexunParse implements ParseData {


    @Override
    public List<K> parse(String dataStr) {

        // String s = {"KLine":[{"Time":"时间"},{"LastClose":"前收盘价"},{"Open":"开盘价"},{"Close":"收盘价"},{"High":"最高价"},{"Low":"最低价"},{"Volume":"成交量"},{"Amount":"成交额"}],"TABLE":[{"KLine[]":"K线列表"},{"BeginTime":"最早时间"},{"EndTime":"最后时间"},{"Total":"总数"},{"PriceWeight":"价格倍数"}],"Data":[[[20200102000000,3040,3125,3131,3322,3098,100640442,3222438318],[20200103000000,3131,3100,3107,3176,3056,59029028,1834871604],[20200106000000,3107,3030,3091,3173,3013,58455559,1812847769],[20200107000000,3091,3098,3186,3250,3070,67427014,2123608649],[20200108000000,3186,3120,3058,3156,3033,67639777,2092772479],[20200109000000,3058,3138,3100,3183,3073,51576880,1613398096],[20200110000000,3100,3180,3131,3222,3106,50994755,1608248097],[20200113000000,3131,3130,3187,3187,3010,60593937,1876369885],[20200114000000,3187,3192,3108,3199,3101,43272007,1363849144],[20200115000000,3108,3107,3068,3119,3050,31452355,967620268]],20180620000000,20200430000000,455,100]});

        List<K> rtn = Lists.newArrayList();

        if (StringUtils.isNotBlank(dataStr)) {
            dataStr = dataStr.substring(1, dataStr.length() - 2);
            ObjectMapper mapper = new ObjectMapper();

            try {
                JsonNode allData = mapper.readTree(dataStr);
                JsonNode data = allData.get("Data");
                JsonNode first = data.get(0);

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                if (first.isArray()) {
                    for (JsonNode x : first) {

                        if (x.isArray()) {
                            // [20200106000000,3107,3030,3091,3173,3013,58455559,1812847769]
                            // [{"Time":"时间"},{"LastClose":"前收盘价"},{"Open":"开盘价"},{"Close":"收盘价"},{"High":"最高价"},{"Low":"最低价"},{"Volume":"成交量"},{"Amount":"成交额"}]
                            String date = x.get(0).asText();
                            int open = x.get(2).asInt();
                            int close = x.get(3).asInt();
                            int high = x.get(4).asInt();
                            int low = x.get(5).asInt();
                            long volume = x.get(6).asLong();
                            long amount = x.get(7).asLong();

                            K k = K.builder().timeBegin(LocalDateTime.parse(date, dateTimeFormatter))
                                    .timeEnd(LocalDateTime.parse(date, dateTimeFormatter))
                                    .open((double) open / 100)
                                    .close((double) close / 100)
                                    .high((double) high / 100)
                                    .low((double) low / 100)
                                    .volume((double) volume)
                                    .amount((double) amount).build();

                            rtn.add(k);
                        }
                    }
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return rtn;
    }
}
