package com.kiluet.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Sums {

	public static void main(String[] args) throws Exception {

		List<Sum> sumList = Arrays.asList(new Sum(0, 10), new Sum(100, 1_000),
				new Sum(10_000, 1_000_000));
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		List<Future<Long>> results = executor.invokeAll(sumList);
		
		executor.shutdown();

		for (Future<Long> result : results) {
			System.out.println(result.get());
		}
		
	}

}
