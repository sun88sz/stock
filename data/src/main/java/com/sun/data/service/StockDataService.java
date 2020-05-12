package com.sun.data.service;

import com.sun.common.bean.K;
import com.sun.common.bean.Typing;
import com.sun.czsc.Bi;
import com.sun.data.bean.KData;
import com.sun.data.bean.KPo;
import com.sun.data.dao.KDao;
import com.sun.data.web.GetData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sun
 */
@Service
public class StockDataService {

    @Autowired
    private KDao kDao;
    @Autowired
    @Qualifier("hexunClient")
    private GetData hexunClient;

    /**
     * @param code
     * @return
     */
    public List<K> saveLatest(String code) {
        List<K> rtn = null;
        KPo last = kDao.findFirstByCodeOrderByTimeDesc(code);
        if (last != null) {
            // 向后一天
            LocalDateTime time = last.getTime().plusDays(1);
            rtn = hexunClient.getDayData(code, time, null);
        } else {
            LocalDateTime now = LocalDateTime.now();
            // 还没收盘 取前一天
            if (now.getHour() < 15) {
                now = now.plusDays(-1);
            }
            rtn = hexunClient.getDayData(code, null, now);
        }
        if (CollectionUtils.isNotEmpty(rtn)) {
            List<KPo> pos = rtn.stream().map(d -> new KPo().convertFrom(d)).peek(d -> d.setCode(code)).collect(Collectors.toList());
            kDao.saveAll(pos);
        }
        return rtn;
    }

    /**
     * @param code
     * @return
     */
    public List<K> selectAllFromDatabase(String code) {
        KPo kPo = new KPo();
        kPo.setCode(code);

        Sort sort = Sort.by(Sort.Direction.ASC, "time");

        Example<KPo> example = Example.of(kPo);
        List<KPo> all = kDao.findAll(example, sort);
        List<K> collect = all.stream().map(KPo::convertTo).collect(Collectors.toList());
        return collect;
    }


    /**
     * @param code
     * @return
     */
    public List<K> selectAllByCode(String code) {
        saveLatest(code);
        return selectAllFromDatabase(code);
    }

    /**
     *
     * @param code
     * @return
     */
    public KData typing(String code) {
        List<K> ks = selectAllByCode(code);
        Bi bi = new Bi();
        List<K> ks2 = bi.filter(ks);
        List<Typing> typing = bi.typing(ks2);
        List<Typing> line = bi.line(typing);

        KData kData = new KData();
        kData.setKs(ks);
        kData.setTypings(line);

        return kData;
    }

}
