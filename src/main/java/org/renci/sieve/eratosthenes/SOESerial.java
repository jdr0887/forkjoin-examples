package org.renci.sieve.eratosthenes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SOESerial implements Runnable {

    private final boolean[] primeArray;

    private final Integer ceiling;

    public SOESerial(final Integer ceiling) {
        super();
        this.ceiling = ceiling;
        primeArray = new boolean[ceiling + 1];
    }

    @Override
    public void run() {
        System.out.println(String.format("ceiling = %d", ceiling));
        Arrays.fill(primeArray, true);
        long startTime = System.currentTimeMillis();

        for (int i = 2; i * i <= ceiling; i++) {
            if (primeArray[i]) {
                for (int j = i; i * j <= ceiling; j++) {
                    primeArray[i * j] = false;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        startTime = System.currentTimeMillis();
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(new File("/tmp/soe.serial.txt")));
            for (int i = 2; i <= ceiling; i++) {
                if (primeArray[i]) {
                    bw.write(String.format("%d%n", i));
                    bw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to write to disk = %d milliseconds", endTime - startTime));

    }

    public static void main(String[] args) {
        SOESerial runnable = new SOESerial(100000000);
        runnable.run();
    }

}
