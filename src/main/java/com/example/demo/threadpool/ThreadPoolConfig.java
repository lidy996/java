package com.example.demo.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 线程池配置
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

	@Bean
	@Qualifier("newFixedThreadPool")
	public ExecutorService newFixedThreadPool() {
		return Executors.newFixedThreadPool(50);
	}

	@Bean
	@Qualifier("testThreadPool")
	public ExecutorService testThreadPool() {

		Integer corePoolSize = 10;

		Integer maximumPoolSize = 10;

		Long keepAliveTime = 1L;

		TimeUnit unit = TimeUnit.SECONDS;

		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(50);

		RejectedExecutionHandler handler = new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				if (!executor.isShutdown()) {
					try {
						executor.getQueue().put(r);
					}
					catch (InterruptedException e) {
						log.error(e.getMessage());
					}
				}

			}
		};

		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, blockingQueue, handler);
		return pool;
	}
}
