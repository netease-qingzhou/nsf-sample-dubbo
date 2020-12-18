package com.netease.cloud.nsf.demo.stock.dubbo.echo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.netease.cloud.nsf.demo.stock.api.EchoService;
import com.netease.cloud.nsf.demo.stock.api.entity.Stock;

import java.util.List;
import java.util.Map;

@Service(group = "A")
public class EchoServiceImpl implements EchoService {

    private int count = 0;

    @Override
    public String echo() {

        String ip = RpcContext.getContext().getLocalHost();
        int port = RpcContext.getContext().getLocalPort();

        return "greeting from dubbo A echo service " + ip + ":" + port;
    }

    @Override
    public String echoStrAndInt(String str, Integer num) {
        String ip = RpcContext.getContext().getLocalHost();
        int port = RpcContext.getContext().getLocalPort();

        return "A echo service  -- " + str + num + " from " + ip + ":" + port ;
    }

    @Override
    public String echoStrAndInt1(String str, Integer num) {

        if (str.equals("error") && count++ % 5 != 0) {
            throw new RuntimeException("something error");
        }

        String ip = RpcContext.getContext().getLocalHost();
        int port = RpcContext.getContext().getLocalPort();

        return "A echo service  -- " + str + num + " from " + ip + ":" + port + " count:" + count;
    }

    @Override
    public void echoForReturnVoid(String str) {
        System.out.println(str);
    }

    @Override
    public List<String> echoForList(List<String> list) {
        return list;
    }

    @Override
    public Map<String, Object> echoForMap(Map<String, Object> map) {
        return map;
    }

    @Override
    public Stock echoForStock(String str, Stock stock) {
        stock.setName(stock.getName() + str);
        return stock;
    }

    private String fallback(Throwable t){
        String ip = RpcContext.getContext().getLocalHost();
        int port = RpcContext.getContext().getLocalPort();
        return "fallback in dubbo A echo service " + ip + ":" + port;
    }
}
