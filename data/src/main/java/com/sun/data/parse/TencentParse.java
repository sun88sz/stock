package com.sun.data.parse;

import com.google.common.collect.Lists;
import com.sun.common.bean.K;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Sun
 */
@Service
public class TencentParse implements ParseData {

    @Override
    public List<K> parse(String data) {

        List<K> rtn = Lists.newArrayList();

        String[] datas = data.split("\\n");

        for (int i = 2; i < datas.length - 1; i++) {
            String s = datas[i];
            s = "20" + s.replaceAll("\\\\n\\\\", "");

            // 191202 22.80 22.44 22.98 22.41 273926

            String[] line = s.split(" ");
            String dateStr = line[0];
            String start = line[1];
            String end = line[2];
            String high = line[3];
            String low = line[4];
            String quantity = line[5];

            DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

            K.KBuilder builder = K.builder();
            LocalDateTime time = LocalDateTime.from(LocalDate.parse(dateStr, yyyyMMdd).atStartOfDay());
            K k = builder.timeBegin(time).timeEnd(time)
                    .low(Double.parseDouble(low))
                    .high(Double.parseDouble(high))
                    .open(Double.parseDouble(start))
                    .close(Double.parseDouble(end)).build();

            rtn.add(k);
        }

        return rtn;
    }
}
