package org.renci.euler;

import java.util.SortedSet;

import org.renci.sieve.atkins.SOAParallel;

public abstract class AbstractProblem {

    protected final SortedSet<Integer> primeSet;

    public AbstractProblem() {
        super();
        SOAParallel callable = new SOAParallel(100000000);
        primeSet = callable.call();
    }

}
