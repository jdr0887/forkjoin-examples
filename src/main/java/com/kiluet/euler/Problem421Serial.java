package com.kiluet.euler;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class Problem421Serial extends AbstractProblem implements Runnable {

    public Problem421Serial() {
        super();
    }

    @Override
    public void run() {
        BigInteger total = BigInteger.valueOf(0);

        long startTime = System.currentTimeMillis();
        Double m = Double.valueOf(Math.pow(10, 8));

        for (int i = 2; i < Double.valueOf(Math.pow(10, 3)).intValue(); i++) {

            double n = Math.pow(i, 15) + 1;

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

            for (Double factor : factors) {
                if (primeSet.contains(factor.intValue()) && factor < m) {
                    total = total.add(BigInteger.valueOf(factor.longValue()));
                }
            }

        }

        long endTime = System.currentTimeMillis();
        System.out.println(total.toString());
        System.out.println(String.format("duration to calculate = %d seconds", (endTime - startTime) / 1000));
    }

    public static void main(String[] args) {
        Problem421Serial runnable = new Problem421Serial();
        runnable.run();
    }
}
