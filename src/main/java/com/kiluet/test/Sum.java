package com.kiluet.test;

import java.util.concurrent.Callable;

public class Sum implements Callable<Long> {

	private final long from;

	private final long to;

	public Sum(long from, long to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public Long call() {
		long acc = 0;
		for (long i = from; i <= to; i++) {
			acc = acc + i;
		}
		return acc;
	}
}
