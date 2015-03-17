package org.renci.euler;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem421Serial extends AbstractProblem implements Runnable {

    private BigInteger total = BigInteger.valueOf(0);

    private final Integer limit = Double.valueOf(Math.pow(10, 6)).intValue();

    private final Integer m = Double.valueOf(Math.pow(10, 3)).intValue();

    public Problem421Serial() {
        super();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        for (int n = 2; n <= limit; n++) {

            if (n % 1000 == 0) {
                System.out.println(String.format("n = %s, total = %s", n, total.toString()));
            }
            // Set<Integer> factors = new HashSet<Integer>();

            // int n = 10;
            double value = Math.pow(n, 15) + 1;

            // List<Integer> unknownPrimeFactorList = Arrays.asList(
            // n + 1,
            // Double.valueOf(Math.pow(n, 2) - n + 1).intValue(),
            // Double.valueOf(Math.pow(n, 4) - Math.pow(n, 3) + Math.pow(n, 2) - n + 1).intValue(),
            // Double.valueOf(
            // Math.pow(n, 8) + Math.pow(n, 7) - Math.pow(n, 5) - Math.pow(n, 4) - Math.pow(n, 3) + n + 1)
            // .intValue());
            //
            // Set<Integer> knownPrimeFactorSet = new HashSet<Integer>();
            // for (Integer primeFactor : unknownPrimeFactorList) {
            // if (primeSet.contains(primeFactor)) {
            // knownPrimeFactorSet.add(primeFactor);
            // }
            // }
            //
            // for (Integer prime : knownPrimeFactorSet) {
            // while (value % prime == 0) {
            // if (prime < m) {
            // total = total.add(BigInteger.valueOf(prime));
            // }
            // // factors.add(prime);
            // value /= prime;
            // }
            // }
            // System.out.println(String.format("duration to find known prime factors = %d milliseconds",
            // System.currentTimeMillis() - startTime));

            for (Integer prime : primeSet) {
                // if (!knownPrimeFactorSet.contains(prime)) {
                while (value % prime == 0) {
                    if (prime < m) {
                        total = total.add(BigInteger.valueOf(prime));
                    }
                    // factors.add(prime);
                    value /= prime;
                }
                // }
            }

            // for (int j = 2; j <= value; j++) {
            // if (value % j == 0 && !knownPrimeFactorSet.contains(j)) {
            // factors.add(j);
            // value /= j;
            // }
            // }

            // System.out.println(String.format("duration to find remaining factors = %d milliseconds",
            // System.currentTimeMillis() - startTime));

            // for (Integer factor : factors) {
            // if (factor < m) {
            // }
            // }

        }

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        System.out.println(total.toString());
    }

    public static void main(String[] args) {
        Problem421Serial runnable = new Problem421Serial();
        runnable.run();
    }
}
