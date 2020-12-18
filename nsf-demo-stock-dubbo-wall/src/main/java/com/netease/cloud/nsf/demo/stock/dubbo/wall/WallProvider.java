package com.netease.cloud.nsf.demo.stock.dubbo.wall;

import java.util.concurrent.CountDownLatch;
import java.net.*;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.netease.cloud.nsf.demo.stock.dubbo.wall.config.WallConfig;

public class WallProvider {

	public static CountDownLatch SHUTDOWN_LATCH = new CountDownLatch(1);

	public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WallConfig.class);
		context.start();
		SHUTDOWN_LATCH.await();
	}

}
