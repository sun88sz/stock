package com.sun.data;

import com.sun.common.bean.K;
import com.sun.data.service.StockDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DataApplication.class})// 指定启动类
public class ServiceTest {

    @Autowired
    private StockDataService stockDataService;

    @Test
    public void save(){
        stockDataService.save("601066");
    }


    @Test
    public void selectAll(){
        List<K> ks = stockDataService.selectAll("601066");

        System.out.println(ks);
    }
}
