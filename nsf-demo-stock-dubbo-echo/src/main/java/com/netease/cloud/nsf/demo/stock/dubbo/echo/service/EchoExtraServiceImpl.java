package com.netease.cloud.nsf.demo.stock.dubbo.echo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.netease.cloud.nsf.demo.stock.api.EchoExtraService;

/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2019/10/15
 **/
@Service
public class EchoExtraServiceImpl implements EchoExtraService {

    @Override
    public String echo() {
        String ip = RpcContext.getContext().getLocalHost();
        int port = RpcContext.getContext().getLocalPort();

        return "greeting from dubbo A echo extra! service " + ip + ":" + port;
    }

}
