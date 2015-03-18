package com.kiluet.euler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Problem27Parallel extends AbstractProblem implements Runnable {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private int limit = 1000000;

    private int aMax = 0, bMax = 0, nMax = 0;

    public Problem27Parallel() {
        super();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        forkJoinPool.invoke(new OuterTask());
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        System.out.println(String.format("aMax = %d, bMax = %d, nMax = %d", aMax, bMax, nMax));

    }

    class OuterTask extends RecursiveTask<Void> {

        private static final long serialVersionUID = -5582589149573253752L;

        @Override
        protected Void compute() {

            List<RecursiveTask<Void>> forks = new LinkedList<RecursiveTask<Void>>();
            for (int a = -limit + 1; a < limit; a = a + 2) {
                RecursiveTask<Void> task = new InnerTask(a);
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

        private int a;

        public InnerTask(int a) {
            super();
            this.a = a;
        }

        @Override
        protected Void compute() {
            for (int b = 1; b < limit; b = b + 2) {

                if (!primeSet.contains(b)) {
                    continue;
                }

                int n = 0;
                while (primeSet.contains(Math.abs(n * n + a * n + b))) {
                    n++;
                }

                if (n > nMax) {
                    aMax = a;
                    bMax = b;
                    nMax = n;
                }

            }

            return null;
        }
    }

    public static void main(String[] args) {
        Problem27Parallel runnable = new Problem27Parallel();
        runnable.run();
    }
}
