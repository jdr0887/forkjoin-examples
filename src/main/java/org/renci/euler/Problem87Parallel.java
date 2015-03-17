package org.renci.euler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Problem87Parallel extends AbstractProblem implements Runnable {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private Integer ceiling = 50000000;

    public Problem87Parallel() {
        super();
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();
        Iterator<Integer> primeIter = primeSet.iterator();
        double squareRootOfCeiling = Math.sqrt(ceiling);
        while (primeIter.hasNext()) {
            Integer prime = primeIter.next();
            if (prime > squareRootOfCeiling) {
                primeIter.remove();
            }
        }
        int count = forkJoinPool.invoke(new OuterTask());
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        System.out.println(count);

    }

    class OuterTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = -5582589149573253752L;

        @Override
        protected Integer compute() {

            List<RecursiveTask<Integer>> forks = new LinkedList<RecursiveTask<Integer>>();

            for (Integer primeSquare : primeSet) {
                double primeSquared = Math.pow(primeSquare, 2);
                for (Integer primeCube : primeSet) {
                    double primeCubed = Math.pow(primeCube, 3);
                    RecursiveTask<Integer> task = new InnerTask(primeSquared, primeCubed);
                    forks.add(task);
                    task.fork();
                }
            }

            int total = 0;
            for (RecursiveTask<Integer> task : forks) {
                total += task.join();
            }

            return total;
        }
    }

    class InnerTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = -7928670821687785005L;

        private double primeSquared;

        private double primeCubed;

        public InnerTask(double primeSquared, double primeCubed) {
            super();
            this.primeSquared = primeSquared;
            this.primeCubed = primeCubed;
        }

        @Override
        protected Integer compute() {
            int count = 0;
            for (Integer primeFourth : primeSet) {
                double primeFourthed = Math.pow(primeFourth, 4);
                double value = primeSquared + primeCubed + primeFourthed;
                if (value > ceiling) {
                    continue;
                }
                count++;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        Problem87Parallel runnable = new Problem87Parallel();
        runnable.run();
    }
}
