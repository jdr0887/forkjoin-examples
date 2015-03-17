package org.renci.euler;

public class Problem5 extends AbstractProblem implements Runnable {

    public Problem5() {
        super();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
    }

    public static void main(String[] args) {
        Problem5 runnable = new Problem5();
        runnable.run();
    }
}
