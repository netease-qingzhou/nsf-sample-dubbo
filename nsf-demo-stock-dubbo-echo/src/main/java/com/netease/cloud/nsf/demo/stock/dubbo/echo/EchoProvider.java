package com.netease.cloud.nsf.demo.stock.dubbo.echo;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.netease.cloud.nsf.demo.stock.dubbo.echo.config.EchoConfig;

public class EchoProvider {

	public static CountDownLatch SHUTDOWN_LATCH = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EchoConfig.class);
        context.start();
        SHUTDOWN_LATCH.await();
	}
}
