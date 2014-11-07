package primes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Uses the sieve of Eratosthenes to determine the prime numbers
 */
public class SievePrimesGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(SievePrimesGenerator.class);

    public List<Integer> generate(int maxValue) {

        logger.info(String.format("Generating primes using Sieve of Eratosthenes algorithm"));

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

    // An alternative implementation with a shorter loop and adding extra primes at end
    // Slightly worse performance compared to the implementation above.
    public List<Integer> generateAlt(int maxValue) {

        logger.info(String.format("Generating primes using Sieve of Eratosthenes algorithm)"));

        List<Integer> primes = new ArrayList<>();

        // Return empty list if less than 2
        if (maxValue < 2) {
            return primes;
        }

        BitSet primesBits = new BitSet(maxValue + 1);
        primesBits.set(2, maxValue + 1);

        int maxPrimeToCheck = (int)Math.sqrt(maxValue);
        for (long p = 2; p <= maxPrimeToCheck && p > 0; p = primesBits.nextSetBit((int)p+1)) {
            for (long i = p; i <= maxValue; i += p) {
                primesBits.clear((int)i);
            }

            primes.add((int)p);
        }

        // Add on all remaining primes
        primes.addAll(primesBits.stream().boxed().collect(Collectors.toList()));

        return primes;
    }
}
