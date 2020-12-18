package com.netease.cloud.nsf.demo.stock.api;

import com.netease.cloud.nsf.demo.stock.api.entity.Stock;

import java.util.List;
import java.util.Map;

public interface EchoService {

	String echo();

	String echoStrAndInt(String str, Integer num);

	String echoStrAndInt1(String str, Integer num);

	void echoForReturnVoid(String str);

	List<String> echoForList(List<String> list);

	Map<String,Object> echoForMap(Map<String,Object> map);

	Stock echoForStock(String str,Stock stock);

}
