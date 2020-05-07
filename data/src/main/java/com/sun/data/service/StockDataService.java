package com.sun.data.service;

import com.sun.common.bean.K;
import com.sun.data.bean.KPo;
import com.sun.data.dao.KDao;
import com.sun.data.web.GetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public void save(String code) {

        List<K> data = hexunClient.getData(code);

        List<KPo> pos = data.stream().map(d -> new KPo().convertFrom(d)).peek(d -> d.setCode(code)).collect(Collectors.toList());

        kDao.saveAll(pos);
    }

    /**
     *
     * @param code
     * @return
     */
    public List<K> selectAll(String code) {
        KPo kPo = new KPo();
        kPo.setCode(code);

        Sort sort =  Sort.by(Sort.Direction.ASC, "time");

        Example<KPo> example = Example.of(kPo);
        List<KPo> all = kDao.findAll(example,sort);
        List<K> collect = all.stream().map(KPo::convertTo).collect(Collectors.toList());
        return collect;
    }


}
