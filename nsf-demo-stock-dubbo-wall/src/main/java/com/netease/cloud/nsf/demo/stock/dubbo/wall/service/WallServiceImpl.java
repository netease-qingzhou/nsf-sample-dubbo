package com.netease.cloud.nsf.demo.stock.dubbo.wall.service;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.cloud.nsf.demo.stock.api.EchoExtraService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.netease.cloud.nsf.demo.stock.api.EchoService;
import com.netease.cloud.nsf.demo.stock.api.WallService;
import com.netease.cloud.nsf.demo.stock.api.entity.Stock;
import com.netease.cloud.nsf.demo.stock.dubbo.wall.util.CastKit;

@Service
public class WallServiceImpl implements WallService {

    public static final Logger logger = LoggerFactory.getLogger(WallServiceImpl.class.getName());

    @Value("${stock_provider_url}")
    String stockProviderUrl;

    @Value("${stock_advisor_url}")
    String stockAdvisorUrl;

    @Reference(group = "A", lazy = true)
    EchoService echoService;

    @Reference(group = "B", lazy = true)
    EchoService anotherEchoService;

    @Reference(lazy = true)
    EchoExtraService echoExtraService;

    private static final String STOCK_IDS = "sh601857,sh601398,sh600019";

    @Override
    public List<Stock> getStockList(int delay) {

        List<Stock> stocks = new ArrayList<>();
        try {
            String result = sendRequest(stockProviderUrl + "/stocks/" + STOCK_IDS + "?delay=" + delay);
            stocks = CastKit.str2StockList(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stocks;
    }

    @Override
    public List<Stock> getHotAdvice() throws Exception {

        List<Stock> stocks = new ArrayList<>();
        try {
            String result = sendRequest(stockAdvisorUrl + "/advices/hot");
            stocks = CastKit.str2StockList(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return stocks;
    }

    @Override
    public Stock getStockById(String stockId) {

        Stock stock = null;
        try {
            String result = sendRequest(stockProviderUrl + "/stocks/" + stockId);
            stock = CastKit.str2Stock(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stock;
    }

    @Override
    public String echoAdvisor() {

        String result = echoExtraService.echo();
        System.out.println(result);
        return result;
    }

    @Override
    public String echoProvider() {

        String result = echoService.echo();
        System.out.println(result);
        return result;
    }

    @Override
    public String getConfig(String namespace) {
        Config config = ConfigService.getConfig(namespace);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : config.getPropertyNames()) {
            stringBuilder.append(key + " ==> " + config.getProperty(key, "") + "<br>");
        }
        return stringBuilder.toString();
    }

    @Override
    public Object getDebugConfig(String preFix, String type) {
        try {
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass("com.netease.cloud.nsf.agent.configdebuginfo.ConfigDebugManager");
            Method getConfig = clazz.getDeclaredMethod("getDebugConfig", String.class, String.class);
            Object obj = getConfig.invoke(null, preFix, type);
            if (!(obj instanceof String)) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(obj);
            }
            return obj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "com.netease.cloud.nsf.agent.configdebuginfo.ConfigDebugManager not find";
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return "method getDebugConfig not find";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "error !!!";
    }

    @Override
    public String echoStrAndInt(String key, int num) {
        String result;
        try {
            result = echoService.echoStrAndInt(key, num);
        } catch (Exception e) {
            logger.error("echoStrAndInt error", e);
            result = e.getMessage();
        }
        return result;
    }

    @Override
    public String echoStrAndInt1(String key, int num) {
        String result;
        try {
            result = echoService.echoStrAndInt1(key, num);
        } catch (Exception e) {
            logger.error("echoStrAndInt1 error", e);
            result = e.getMessage();
        }
        return result;
    }

    @Override
    public String echoAdvisorB() {
        return anotherEchoService.echo();
    }

    @Override
    public String echoProviderB() {
        return anotherEchoService.echo();
    }

    @Override
    public String echoStrAndIntB(String key, int num) {
        String result;
        try {
            result = anotherEchoService.echoStrAndInt(key, num);
        } catch (Exception e) {
            logger.error("echoStrAndInt error", e);
            result = e.getMessage();
        }
        return result;
    }

    @Override
    public String echoStrAndInt1B(String key, int num) {
        String result;
        try {
            result = anotherEchoService.echoStrAndInt1(key, num);
        } catch (Exception e) {
            logger.error("echoStrAndInt1 error", e);
            result = e.getMessage();
        }
        return result;
    }


    private String sendRequest(String url) {

        CloseableHttpClient client = HttpClients.createDefault();
        String result = null;
        try {
            CloseableHttpResponse response = client.execute(new HttpGet(url));
            result = parseResponseBody(response.getEntity(), StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private String parseResponseBody(HttpEntity entity, String encoding) {
        if (entity == null) {
            return null;
        }
        try (InputStream input = entity.getContent()) {
            return IOUtils.toString(input, encoding);
        } catch (Exception e) {
//			logger.info("get httpclient response error.", e);
            e.printStackTrace();
        }
        return null;
    }

    private String fallback(Throwable t) {
        return "fallback in dubbo wall service";
    }
}
