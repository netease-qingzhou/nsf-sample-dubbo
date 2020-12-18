package com.netease.cloud.nsf.demo.stock.viewer.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.config.annotation.Reference;
import com.netease.cloud.nsf.demo.stock.api.WallService;
import com.netease.cloud.nsf.demo.stock.api.entity.Stock;
import com.netease.cloud.nsf.demo.stock.viewer.web.manager.LogManager;

@RestController
@RequestMapping("/dubbo")
@ConditionalOnProperty(name="dubbo", havingValue="true")
public class DubboController {

	private static Logger log = LoggerFactory.getLogger(DubboController.class);

    @Reference(timeout = 3000, lazy = true)
    WallService wallService;

    @GetMapping(value = "/stocks", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getStockList(@RequestParam(name = "delay", required = false, defaultValue = "0") int delay) {

    	Map<String, Object> resultMap = new HashMap<>();
        List<Stock> stocks = new ArrayList<>();
        try {
            stocks = wallService.getStockList(delay);
        } catch (Exception e) {
            log.warn("get stock list failed ...");
        }
        resultMap.put("stocks", stocks);
        return resultMap;
    }

    @GetMapping(value = "/advices/hot", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getHotAdvice() {

    	Map<String, Object> resultMap = new HashMap<>();
        List<Stock> stocks = new ArrayList<>();
        try {
            stocks = wallService.getHotAdvice();
        } catch (Exception e) {
            log.warn("get hot stock advice failed ...");
            Stock stock = new Stock();
			stock.setName("Cannot get any advice from dubbo service");
			stocks.add(stock);
			resultMap.put("stocks", stocks);
			return resultMap;
        }
        resultMap.put("stocks", stocks);
        return resultMap;
    }

    @GetMapping("/stocks/{stockId}")
    @ResponseBody
    public Stock getStockById(@PathVariable String stockId) {

        Stock stock = null;
        try {
            stock = wallService.getStockById(stockId);
        } catch (Exception e) {
            log.warn("get stock[{}] info failed ...", stockId);
        }
        return stock;
    }
    
    @GetMapping("/echo/advisor")
    @ResponseBody
    public String echoAdvisor(HttpServletRequest request) {
    	String result = wallService.echoAdvisor();
    	LogManager.put(UUID.randomUUID().toString(), result);
    	return result;
    }
    
    @GetMapping("/echo/provider")
    @ResponseBody
    public String echoProvider(HttpServletRequest request) {
    	String result = wallService.echoProvider();
    	LogManager.put(UUID.randomUUID().toString(), result);
    	return result;
    }

    @GetMapping("/getConfigs")
    @ResponseBody
    public String getConfig(@RequestParam String namespace){
        return wallService.getConfig(namespace);
    }

    @GetMapping("/getDebugConfigs")
    public Object getConfig(@RequestParam(name = "key", required = false, defaultValue = "") String preFix,
                            @RequestParam(name = "type", required = false, defaultValue = "html") String type){
        return wallService.getDebugConfig(preFix, type);
    }

    @GetMapping("/echoStr")
    @ResponseBody
    public String echoStr(@RequestParam String key,
                          @RequestParam int num) {

        String result = wallService.echoStrAndInt(key, num);
        LogManager.put(UUID.randomUUID().toString(), result);
        return result;
    }

    @GetMapping("/echoStr1")
    @ResponseBody
    public String echoStr1(@RequestParam String key,
                          @RequestParam int num) {

        String result = wallService.echoStrAndInt1(key, num);
        LogManager.put(UUID.randomUUID().toString(), result);
        return result;
    }



    @GetMapping("/echo/advisorB")
    @ResponseBody
    public String echoAdvisorB(HttpServletRequest request) {
        String result = wallService.echoAdvisorB();
        LogManager.put(UUID.randomUUID().toString(), result);
        return result;
    }

    @GetMapping("/echo/providerB")
    @ResponseBody
    public String echoProviderB(HttpServletRequest request) {
        String result = wallService.echoProviderB();
        LogManager.put(UUID.randomUUID().toString(), result);
        return result;
    }

    @GetMapping("/echoStrB")
    @ResponseBody
    public String echoStrB(@RequestParam String key,
                          @RequestParam int num) {

        String result = wallService.echoStrAndIntB(key, num);
        LogManager.put(UUID.randomUUID().toString(), result);
        return result;
    }

    @GetMapping("/echoStr1B")
    @ResponseBody
    public String echoStr1B(@RequestParam String key,
                           @RequestParam int num) {

        String result = wallService.echoStrAndInt1B(key, num);
        LogManager.put(UUID.randomUUID().toString(), result);
        return result;
    }

}
