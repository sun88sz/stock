package com.sun.data;

import com.sun.common.bean.K;
import com.sun.data.service.StockDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DataApplication.class})// 指定启动类
public class ServiceTest {

    @Autowired
    private StockDataService stockDataService;

    @Test
    @Transactional
    public void save() {
        String code = "601066";
//        String code = "002707";
        List<K> ks = stockDataService.saveLatest(code);
    }


    @Test
    @Transactional
    public void selectAll() {
        List<K> ks = stockDataService.selectAllFromDatabase("601066");
        System.out.println(ks);
    }
}
