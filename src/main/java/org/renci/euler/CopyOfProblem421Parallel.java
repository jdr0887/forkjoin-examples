package org.renci.euler;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 2304215802083466198
 * 
 * @author jdr0887
 * 
 */
public class CopyOfProblem421Parallel extends AbstractProblem implements Runnable {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private final Integer limit = Double.valueOf(Math.pow(10, 6)).intValue();

    private final Integer m = Double.valueOf(Math.pow(10, 3)).intValue();

    private BigInteger total = BigInteger.valueOf(0);

    public CopyOfProblem421Parallel() {
        super();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        forkJoinPool.invoke(new OuterTask());
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        System.out.println(total.toString());
    }

    class OuterTask extends RecursiveTask<Void> {

        private static final long serialVersionUID = -5582589149573253752L;

        @Override
        protected Void compute() {

            List<RecursiveTask<BigInteger>> forks = new LinkedList<RecursiveTask<BigInteger>>();

            for (int n = 2; n <= limit; ++n) {
                int value = Double.valueOf(Math.pow(n, 15) + 1).intValue();
                RecursiveTask<BigInteger> task = new InnerTask(value);
                forks.add(task);
                task.fork();
            }

            for (RecursiveTask<BigInteger> task : forks) {
                total = total.add(task.join());
            }

            return null;
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

            BigInteger ret = BigInteger.valueOf(0);
            // Set<Integer> factors = new HashSet<Integer>();

            for (Integer prime : primeSet) {
                // if (!knownPrimeFactorSet.contains(prime)) {
                while (value % prime == 0) {
                    if (prime < m) {
                        ret = ret.add(BigInteger.valueOf(prime));
                        // total = total.add(BigInteger.valueOf(prime));
                    }
                    // factors.add(prime);
                    value /= prime;
                }
                // }
            }

            // for (int j = 2; j <= value; j++) {
            // while (value % j == 0) {
            // factors.add(j);
            // value /= j;
            // }
            // }

            // for (Integer integer : factors) {
            // if (integer < m) {
            // ret = ret.add(BigInteger.valueOf(integer));
            // }
            // }

            return ret;
        }
    }

    public static void main(String[] args) {
        CopyOfProblem421Parallel runnable = new CopyOfProblem421Parallel();
        runnable.run();
    }
}
