package primes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Uses the sieve of Eratosthenes to determine the prime numbers
 */
public class SievePrimesGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(SievePrimesGenerator.class);

    public List<Integer> generate(int maxValue) {

        logger.info(String.format("Generating primes using Sieve of Eratosthenes algorithm)"));

        List<Integer> primes = new ArrayList<Integer>();
        BitSet nonPrimes = new BitSet(maxValue + 1);

        for (long p = 2; p <= maxValue ; p = nonPrimes.nextClearBit((int)p+1)) {
            for (long i = p * p; i <= maxValue; i += p) {
                nonPrimes.set((int)i);
            }

            primes.add((int)p);
        }

        return primes;
    }
}
