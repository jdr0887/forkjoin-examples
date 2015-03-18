package com.kiluet.euler;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class Scratch {

    @Test
    public void scratch() {
        Set<Integer> factors = new HashSet<>();
        int n = Double.valueOf(Math.pow(2, 15) + 1).intValue();

        int num = n;
        while (num % 2 == 0) {
            factors.add(num);
            num /= 2;
        }
        int j = 3;
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

        for (Integer factor : factors) {
            System.out.println(factor);
        }
    }

}
