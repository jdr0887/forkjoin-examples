package com.kiluet.euler;

public class Problem27Serial extends AbstractProblem implements Runnable {

    private int aMax = 0, bMax = 0, nMax = 0;

    public Problem27Serial() {
        super();
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();
        int limit = 10000;
        for (int a = -limit + 1; a < limit; a = a + 2) {
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
        }

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        System.out.println(String.format("aMax = %d, bMax = %d, nMax = %d", aMax, bMax, nMax));

    }

    public static void main(String[] args) {
        Problem27Serial runnable = new Problem27Serial();
        runnable.run();
    }
}
