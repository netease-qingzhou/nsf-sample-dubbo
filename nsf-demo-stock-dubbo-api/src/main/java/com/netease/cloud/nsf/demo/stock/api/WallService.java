package com.netease.cloud.nsf.demo.stock.api;

import java.util.List;

import com.netease.cloud.nsf.demo.stock.api.entity.Stock;

public interface WallService {
	
    List<Stock> getStockList(int delay);
    
    List<Stock> getHotAdvice() throws Exception;
    
    Stock getStockById(String stockId);
    
    String echoAdvisor();
    
    String echoProvider();

    String getConfig(String namespace);

    Object getDebugConfig(String preFix, String type);

    String echoStrAndInt(String key, int num);

    String echoStrAndInt1(String key, int num);

    String echoAdvisorB();

    String echoProviderB();

    String echoStrAndIntB(String key, int num);

    String echoStrAndInt1B(String key, int num);
}
