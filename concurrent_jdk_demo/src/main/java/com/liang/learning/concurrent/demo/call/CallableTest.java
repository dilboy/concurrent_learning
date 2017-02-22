package com.liang.learning.concurrent.demo.call;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @description 测试callable接口的使用
   *	@author  Liang
   *	@since 2017年2月22日
**/

public class CallableTest {
	ExecutorService pool = Executors.newCachedThreadPool();
	/**
	 * @description future类的get方法同步等待结果，会阻塞线程，所以虽然是多线程写法，但是效果是同步的。
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testCallableGet() throws InterruptedException, ExecutionException{
		for(int i =0;i<20;i++){
			Future<Integer> future = pool.submit(new CallDemo(i));
			System.out.println(future.get());
		}
	}
	
	
	/**
	 * @description 与 testCallableGet方法对应，实验是不是get引起的阻塞行为
	 */
	@Test
	public void testCallableLastGet(){
		ArrayList<Future<Integer>> futures = new ArrayList<>();
		for(int i =0;i<20;i++){
			Future<Integer> future = pool.submit(new CallDemo(i));
			futures.add(future);
		}
		futures.forEach(x->{
			try {
				System.out.println(x.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
	}
	
}

class CallDemo implements Callable<Integer>{
	private int i;

	public CallDemo(int i) {
		this.i = i;
	}
	
	@Override
	public Integer call() throws Exception {
		System.out.println("this is liang's call demo");
		TimeUnit.SECONDS.sleep(1);
		return i;
	}
	
}
