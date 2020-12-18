package com.netease.cloud.nsf.demo.stock.dubbo.echo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDubbo(scanBasePackages = "com.netease.cloud.nsf.demo.stock.dubbo.echo")
@ComponentScan(value = {"com.netease.cloud.nsf.demo.stock.dubbo.echo"})
public class EchoConfig {

    @Value("${nsf.zk:zookeeper://127.0.0.1:2181}")
    public String zk;

    @Value("${nsf.app:nsf-demo-stock-dubbo-echo}")
    public String app;

    @Value("${nsf.port:10886}")
    public int port;

    @Value("${nsf.app.version:0.0.1}")
    public String version;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName(app);
        application.setVersion(version);
        return application;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(zk);
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(port);
        return protocolConfig;
    }


}
