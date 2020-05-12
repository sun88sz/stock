package com.sun.data.controller;

import com.sun.common.bean.K;
import com.sun.data.bean.KData;
import com.sun.data.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

    @GetMapping(path = "/all")
    public List<K> selectAll(@RequestParam("code") String code, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println(new Date());
        return stockDataService.selectAllByCode(code);
    }

    @GetMapping(path = "/typing")
    public KData typing(@RequestParam("code") String code, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");


        System.out.println(new Date());
        return stockDataService.typing(code);
    }
}
