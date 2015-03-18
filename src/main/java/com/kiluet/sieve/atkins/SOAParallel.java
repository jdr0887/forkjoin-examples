package com.kiluet.sieve.atkins;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SOAParallel implements Callable<SortedSet<Integer>> {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private final boolean[] primeArray;

    private final Integer ceiling;

    public SOAParallel(final Integer ceiling) {
        super();
        this.ceiling = ceiling;
        primeArray = new boolean[ceiling + 1];
    }

    @Override
    public SortedSet<Integer> call() {
        Arrays.fill(primeArray, false);
        long startTime = System.currentTimeMillis();
        primeArray[0] = false;
        primeArray[1] = false;
        primeArray[2] = true;
        primeArray[3] = true;
        forkJoinPool.invoke(new OuterTask());

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate %d primes: %d milliseconds", ceiling, endTime
                - startTime));

        SortedSet<Integer> set = new TreeSet<Integer>();
        for (int i = 0; i <= ceiling; i++) {
            if (primeArray[i]) {
                set.add(i);
            }
        }
        return set;
    }

    class OuterTask extends RecursiveTask<Void> {

        private static final long serialVersionUID = -5582589149573253752L;

        @Override
        protected Void compute() {

            List<RecursiveTask<Void>> forks = new LinkedList<RecursiveTask<Void>>();

            int limitSqrt = (int) Math.sqrt((double) ceiling);

            for (int i = 1; i <= limitSqrt; i++) {
                RecursiveTask<Void> task = new InnerTask(i, limitSqrt);
                forks.add(task);
                task.fork();
            }

            for (RecursiveTask<Void> task : forks) {
                task.join();
            }

            for (int n = 5; n <= limitSqrt; n++) {
                if (primeArray[n]) {
                    int x = n * n;
                    for (int i = x; i <= ceiling; i += x) {
                        primeArray[i] = false;
                    }
                }
            }

            return null;
        }

    }

    class InnerTask extends RecursiveTask<Void> {

        private static final long serialVersionUID = -7928670821687785005L;

        private Integer divisor;

        private Integer limitSqrt;

        public InnerTask(Integer divisor, Integer limitSqrt) {
            super();
            this.divisor = divisor;
            this.limitSqrt = limitSqrt;
        }

        @Override
        protected Void compute() {

            for (int j = 1; j <= limitSqrt; j++) {
                int n = (4 * divisor * divisor) + (j * j);
                if (n <= ceiling && (n % 12 == 1 || n % 12 == 5)) {
                    primeArray[n] = !primeArray[n];
                }
                n = (3 * divisor * divisor) + (j * j);
                if (n <= ceiling && (n % 12 == 7)) {
                    primeArray[n] = !primeArray[n];
                }
                n = (3 * divisor * divisor) - (j * j);
                if (divisor > j && n <= ceiling && (n % 12 == 11)) {
                    primeArray[n] = !primeArray[n];
                }
            }

            return null;
        }

    }

    public static void main(String[] args) {
        SOAParallel runnable = new SOAParallel(100000000);
        runnable.call();
    }

}
