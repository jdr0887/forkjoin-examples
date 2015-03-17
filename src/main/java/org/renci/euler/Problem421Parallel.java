package org.renci.euler;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 2304215802083466198
 * 
 * @author jdr0887
 * 
 */
public class Problem421Parallel extends AbstractProblem implements Runnable {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private final Integer limit = Double.valueOf(Math.pow(10, 4)).intValue();

    private final Integer m = Double.valueOf(Math.pow(10, 2)).intValue();

    private BigInteger total = BigInteger.valueOf(0);

    private BitSet a = new BitSet(limit + 1);

    public Problem421Parallel() {
        super();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int l = forkJoinPool.invoke(new OuterTask());

        BigInteger localTotal = BigInteger.valueOf(0);
        for (int i = l + 1; i <= limit; i++) {
            if (!a.get(i)) {
                for (long n = 1; n <= i; n++) {
                    long wt = m / i;
                    if (n <= (m % i)) {
                        wt++;
                    }
                    if (checkOne(n, i)) {
                        localTotal.add(BigInteger.valueOf(wt * i));
                    }
                }
            }
        }
        total.add(localTotal);

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        System.out.println(total.toString());
    }

    class OuterTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = -5582589149573253752L;

        public OuterTask() {
            super();
        }

        @Override
        protected Integer compute() {

            List<RecursiveTask<BigInteger>> forks = new LinkedList<RecursiveTask<BigInteger>>();

            int l = 0;

            for (int i = 2; i * i <= limit; i = a.nextClearBit(i + 1)) {

                l = i;
                RecursiveTask<BigInteger> task = new InnerTask(i);
                forks.add(task);
                task.fork();

            }

            for (RecursiveTask<BigInteger> task : forks) {
                total = total.add(task.join());
            }

            return l;
        }
    }

    class InnerTask extends RecursiveTask<BigInteger> {

        private static final long serialVersionUID = -7928670821687785005L;

        private Integer value;

        public InnerTask(Integer value) {
            super();
            this.value = value;
        }

        @Override
        protected BigInteger compute() {

            BigInteger localTotal = BigInteger.valueOf(0);
            for (long n = 1; n <= value; n++) {
                long wt = m / value;
                if (n <= (m % value)) {
                    wt++;
                }
                if (checkOne(n, value)) {
                    localTotal.add(BigInteger.valueOf(wt * value));
                }
            }

            return localTotal;
        }
    }

    private boolean checkOne(long n, long p) {

        if ((n % p) == 0) {
            return false;
        }

        long q = n % p;
        for (int i = 0; i < 4; i++) {
            q = (q * q) % p;
        }

        q = (q + n) % p;

        return (q == 0);
    }

    public static void main(String[] args) {
        Problem421Parallel runnable = new Problem421Parallel();
        runnable.run();
    }
}
