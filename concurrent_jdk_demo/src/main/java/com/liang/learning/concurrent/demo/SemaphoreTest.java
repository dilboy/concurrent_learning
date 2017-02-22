package com.liang.learning.concurrent.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @description semaphore是jdk concurrent包中的一个较常用工具类，中文意思是信号量
 *              其作用主要是控制同时能有多少个线程执行任务，避免大爆发情况下太多线程执行当前业务操作。
 * 
 * @author Liang
 * @since 2017年2月22日
 **/
public class SemaphoreTest {

	ThreadFactory threadFactory = new ThreadFactory() {

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setName("liang's threads pool:----" + thread.getName());
			return thread;
		}
	};

	/**
	 * 通过Threadfactory可以控制生成的线程具备哪些特性（如异常捕获、超时等等）
	 */
	ExecutorService pool = Executors.newCachedThreadPool(threadFactory);
//	ExecutorService pool = Executors.newCachedThreadPool();

	/**
	 * @description 测试通过Semaphore控制信号量数量
	 * @conclusion 在线程池数量很大的时候可以通过Semaphore控制当前能执行的线程数量
	 * @throws InterruptedException
	 */
	@Test
	public void testSemaphore() throws InterruptedException {
		int batchSize = 20;
		Semaphore semaphore = new Semaphore(3);
		for (int i = 1; i < batchSize; i++) {
			int j = i;
			pool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("正在执行的线程数" + semaphore.availablePermits());
						semaphore.acquire();
						System.out.println(Thread.currentThread().getName());
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					semaphore.release();
				}
			});
		}
		TimeUnit.SECONDS.sleep(100);
	}
}
