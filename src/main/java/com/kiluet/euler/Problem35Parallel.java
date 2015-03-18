package com.kiluet.euler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Problem35Parallel extends AbstractProblem implements Runnable {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private final Set<Integer> winnerSet = new HashSet<Integer>();

    public Problem35Parallel() {
        super();
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();
        forkJoinPool.invoke(new OuterTask());
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));

        System.out.println(winnerSet.size());
        // System.out.println(prime);
        // for (Integer prime : winnerSet) {
        // }

    }

    class OuterTask extends RecursiveTask<Void> {

        private static final long serialVersionUID = -5582589149573253752L;

        @Override
        protected Void compute() {

            List<RecursiveTask<Void>> forks = new LinkedList<RecursiveTask<Void>>();
            for (Integer prime : primeSet) {
                RecursiveTask<Void> task = new InnerTask(prime);
                forks.add(task);
                task.fork();
            }

            for (RecursiveTask<Void> task : forks) {
                task.join();
            }

            return null;
        }
    }

    class InnerTask extends RecursiveTask<Void> {

        private static final long serialVersionUID = -7928670821687785005L;

        private Integer prime;

        public InnerTask(Integer prime) {
            super();
            this.prime = prime;
        }

        @Override
        protected Void compute() {
            String primeString = prime.toString();
            int digits = primeString.length();
            int foundPrimes = 0;
            for (int i = 0; i < digits; i++) {

                Character firstChar = primeString.charAt(0);
                StringBuilder tmp = new StringBuilder(primeString);
                for (int j = 0; j < digits; j++) {
                    if (j == digits - 1) {
                        break;
                    }
                    tmp.replace(j, j + 1, primeString.substring(j + 1, j + 2));
                }
                tmp.replace(primeString.length() - 1, primeString.length(), firstChar.toString());

                if (primeSet.contains(Integer.valueOf(tmp.toString()))) {
                    foundPrimes++;
                }
                primeString = tmp.toString();

            }

            if (foundPrimes == digits - 1) {
                winnerSet.add(prime);
            }

            return null;
        }
    }

    public static void main(String[] args) {
        Problem35Parallel runnable = new Problem35Parallel();
        runnable.run();
    }
}
