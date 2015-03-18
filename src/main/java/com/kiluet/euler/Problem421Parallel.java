package com.kiluet.euler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 
 * 4921275331
 * duration to calculate = 3 seconds
 * 
 * @author jdr0887
 */
public class Problem421Parallel extends AbstractProblem implements Runnable {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private BigInteger total = BigInteger.valueOf(0);

    public Problem421Parallel() {
        super();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        Double m = Double.valueOf(Math.pow(10, 8));
        forkJoinPool.invoke(new OuterTask(m));
        long endTime = System.currentTimeMillis();
        System.out.println(total.toString());
        System.out.println(String.format("duration to calculate = %d seconds", (endTime - startTime) / 1000));
    }

    class OuterTask extends RecursiveTask<Void> {

        private static final long serialVersionUID = -5582589149573253752L;

        private Double m;

        public OuterTask(Double m) {
            super();
            this.m = m;
        }

        @Override
        protected Void compute() {

            List<RecursiveTask<BigInteger>> forks = new ArrayList<RecursiveTask<BigInteger>>();

            for (int i = 2; i < Double.valueOf(Math.pow(10, 3)).intValue(); i++) {
                double n = Math.pow(i, 15) + 1;
                RecursiveTask<BigInteger> recursiveTask = new InnerTask(n, m);
                forks.add(recursiveTask);
                recursiveTask.fork();
            }

            for (RecursiveTask<BigInteger> task : forks) {
                BigInteger ret = task.join();
                total = total.add(ret);
            }

            return null;
        }
    }

    class InnerTask extends RecursiveTask<BigInteger> {

        private static final long serialVersionUID = -7928670821687785005L;

        private Double n;

        private Double m;

        public InnerTask(Double n, Double m) {
            super();
            this.n = n;
            this.m = m;
        }

        @Override
        protected BigInteger compute() {

            Set<Double> factors = new HashSet<>();

            Double num = n;
            while (num % 2 == 0) {
                factors.add(num);
                num /= 2;
            }
            Double j = 3D;
            while (j <= Math.sqrt(num) + 1) {
                if (num % j == 0) {
                    factors.add(j);
                    num /= j;
                } else {
                    j += 2;
                }
            }
            if (num > 1) {
                factors.add(num);
            }

            BigInteger total = BigInteger.valueOf(0);
            for (Double factor : factors) {
                if (primeSet.contains(factor.intValue()) && factor < m) {
                    total = total.add(BigInteger.valueOf(factor.longValue()));
                }
            }

            return total;
        }

    }

    public static void main(String[] args) {
        Problem421Parallel runnable = new Problem421Parallel();
        runnable.run();
    }
}
