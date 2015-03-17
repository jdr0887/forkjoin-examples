package org.renci.euler;

import java.util.Iterator;

public class Problem87Serial extends AbstractProblem implements Runnable {

    public Problem87Serial() {
        super();
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();
        Integer ceiling = 50000000;
        Iterator<Integer> primeIter = primeSet.iterator();
        double squareRootOfCeiling = Math.sqrt(ceiling);
        while (primeIter.hasNext()) {
            Integer prime = primeIter.next();
            if (prime > squareRootOfCeiling) {
                primeIter.remove();
            }
        }

        int count = 0;
        for (Integer primeSquare : primeSet) {
            double primeSquared = Math.pow(primeSquare, 2);
            for (Integer primeCube : primeSet) {
                double primeCubed = Math.pow(primeCube, 3);
                for (Integer primeFourth : primeSet) {
                    double primeFourthed = Math.pow(primeFourth, 4);
                    double value = primeSquared + primeCubed + primeFourthed;
                    if (value > ceiling) {
                        continue;
                    }
                    count++;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        System.out.println(count);

    }

    public static void main(String[] args) {
        Problem87Serial runnable = new Problem87Serial();
        runnable.run();
    }
}
