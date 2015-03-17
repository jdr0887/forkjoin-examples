package org.renci.euler;

public class Problem4 extends AbstractProblem implements Runnable {

    public Problem4() {
        super();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
    }

    public static void main(String[] args) {
        Problem4 runnable = new Problem4();
        runnable.run();
    }
}
