package com.kiluet.sieve.atkins;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SOASerial implements Runnable {

    private final boolean[] primeArray;

    private final Integer ceiling;

    public SOASerial(final Integer ceiling) {
        super();
        this.ceiling = ceiling;
        primeArray = new boolean[ceiling + 1];
    }

    @Override
    public void run() {
        System.out.println(String.format("ceiling = %d", ceiling));
        Arrays.fill(primeArray, false);
        long startTime = System.currentTimeMillis();
        primeArray[0] = false;
        primeArray[1] = false;
        primeArray[2] = true;
        primeArray[3] = true;

        int limitSqrt = (int) Math.sqrt((double) ceiling);

        for (int i = 1; i <= limitSqrt; i++) {
            for (int y = 1; y <= limitSqrt; y++) {
                int n = (4 * i * i) + (y * y);
                if (n <= ceiling && (n % 12 == 1 || n % 12 == 5)) {
                    primeArray[n] = !primeArray[n];
                }
                n = (3 * i * i) + (y * y);
                if (n <= ceiling && (n % 12 == 7)) {
                    primeArray[n] = !primeArray[n];
                }
                n = (3 * i * i) - (y * y);
                if (i > y && n <= ceiling && (n % 12 == 11)) {
                    primeArray[n] = !primeArray[n];
                }
            }
        }

        for (int n = 5; n <= limitSqrt; n++) {
            if (primeArray[n]) {
                int x = n * n;
                for (int i = x; i <= ceiling; i += x) {
                    primeArray[i] = false;
                }
            }
        }

        for (int i = 0; i <= ceiling; i++) {
            if (primeArray[i]) {
                
            }
        }

    }

    public static void main(String[] args) {
        SOASerial runnable = new SOASerial(100000000);
        runnable.run();
    }

}
