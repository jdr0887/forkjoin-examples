package com.kiluet.euler;

import java.util.HashSet;
import java.util.Set;

public class Problem35Serial extends AbstractProblem implements Runnable {

    public Problem35Serial() {
        super();
    }

    @Override
    public void run() {

        Set<Integer> winnerSet = new HashSet<Integer>();
        long startTime = System.currentTimeMillis();
        for (Integer prime : primeSet) {
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

        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));

        System.out.println(winnerSet.size());
        // System.out.println(prime);
        // for (Integer prime : winnerSet) {
        // }

    }

    public static void main(String[] args) {
        Problem35Serial runnable = new Problem35Serial();
        runnable.run();
    }
}
