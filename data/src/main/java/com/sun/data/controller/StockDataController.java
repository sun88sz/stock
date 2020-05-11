package com.sun.data.controller;

import com.sun.common.bean.K;
import com.sun.data.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

    @GetMapping(path = "/all")
    public List<K> selectAll(@RequestParam("code") String code, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        return stockDataService.selectAllByCode(code);
    }
}
