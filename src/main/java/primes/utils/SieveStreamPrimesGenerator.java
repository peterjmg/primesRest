package primes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.BitSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Uses the sieve of Eratosthenes to determine the prime numbers and streams.
 *
 * This uses a BitSet which is not synchronized
 */
public class SieveStreamPrimesGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(SieveStreamPrimesGenerator.class);

    public List<Integer> generate(int maxValue) {

        logger.info(String.format("Generating primes using Sieve of Eratosthenes algorithm and streams)"));

        List<Integer> primes = new ArrayList<>();

        // Return empty list if less than 2
        if (maxValue < 2) {
            return primes;
        }

        // Create bitset of primes and initialise
        BitSet primesBits = new BitSet(maxValue + 1);
        primesBits.set(2, maxValue + 1);

        // The max prime to check products of
        int maxPrimeToCheck = (int)Math.sqrt(maxValue);
        for (int p = 2; p <= maxPrimeToCheck ; p = primesBits.nextSetBit(p+1)) {

            final int currentPrime = p;

            // Clear all products of prime including original prime
            // This cannot be parallelized because BitSet is not synchronized
            primesBits.stream().
                    filter(n -> n % currentPrime == 0).
                    forEach(primesBits::clear);

            // Add the current prime
            primes.add(currentPrime);
        }

        // Add on all remaining primes
        primes.addAll(primesBits.stream().boxed().collect(Collectors.toList()));

        return primes;
    }
}
