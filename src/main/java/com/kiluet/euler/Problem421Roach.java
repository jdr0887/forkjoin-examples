package com.kiluet.euler;

import java.util.BitSet;

public class Problem421Roach {

    public static boolean checkOne(long n, long p) {

        if ((n % p) == 0) {
            return false;
        }

        long q = n % p;
        for (int i = 0; i < 4; i++) {
            q = (q * q) % p;
        }

        q = (q + n) % p;

        return (q == 0);
    }

    public static long checkAll(long nMax, long p) {

        long sum = 0;
        for (long n = 1; n <= p; n++) {
            long wt = nMax / p;
            if (n <= (nMax % p)) {
                wt++;
            }
            if (checkOne(n, p)) {
                sum += wt * p;
            }
        }

        return sum;
    }

    public static void main(String[] args) {

        long nMax = Long.parseLong(args[0]);
        int pMax = Integer.parseInt(args[1]);

        BitSet a = new BitSet(pMax + 1);

        long sum = 0;
        int l = 0;
        for (int i = 2; i * i <= pMax; i = a.nextClearBit(i + 1)) {

            l = i;
            sum += checkAll(nMax, i);

            for (int j = i * i; j <= pMax; j += i) {
                a.set(j);
            }
        }

        for (int i = l + 1; i <= pMax; i++) {
            if (!a.get(i)) {
                sum += checkAll(nMax, i);
            }
        }

        System.out.printf("%d\n", sum);
    }
}
