package com.netease.cloud.nsf.demo.stock.dubbo.wall.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableDubbo(scanBasePackages = "com.netease.cloud.nsf.demo.stock.dubbo.wall")
@ComponentScan(value = {"com.netease.cloud.nsf.demo.stock.dubbo.wall"})
public class WallConfig {

    @Value("${nsf.zk:zookeeper://127.0.0.1:2181}")
    public String zk;

    @Value("${nsf.app:nsf-demo-stock-dubbo-wall}")
    public String app;

    @Value("${nsf.port:20384}")
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
