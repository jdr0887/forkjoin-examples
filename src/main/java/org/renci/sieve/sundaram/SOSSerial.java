package org.renci.sieve.sundaram;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SOSSerial implements Runnable {

    private final boolean[] primeArray;

    private final Integer ceiling;

    public SOSSerial(final Integer ceiling) {
        super();
        this.ceiling = ceiling;
        this.primeArray = new boolean[ceiling];
    }

    @Override
    public void run() {
        System.out.println(String.format("ceiling = %d", ceiling));
        Arrays.fill(primeArray, true);
        long startTime = System.currentTimeMillis();
        int n = ceiling / 2;

        for (int i = 1; i < n; i++) {
            for (int j = i; j <= (n - i) / (2 * i + 1); j++) {
                primeArray[i + j + 2 * i * j] = false;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("duration to calculate = %d milliseconds", endTime - startTime));
        startTime = System.currentTimeMillis();
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(new File("/tmp/sos.serial.txt")));
            bw.write("2 3 ");
            for (int i = 2; i < primeArray.length / 2; i++) {
                if (primeArray[i]) {
                    bw.write(String.format("%d%n", (2 * i + 1)));
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
        SOSSerial runnable = new SOSSerial(100000000);
        runnable.run();
    }

}
