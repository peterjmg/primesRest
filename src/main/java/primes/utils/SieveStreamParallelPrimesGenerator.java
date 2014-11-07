package primes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import primes.exception.PrimesException;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Uses the sieve of Eratosthenes to determine the prime numbers
 */
public class SieveStreamParallelPrimesGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(SieveStreamParallelPrimesGenerator.class);

    public List<Integer> generate(int maxValue) {

        logger.info(String.format("Generating primes using Sieve of Eratosthenes algorithm and parallel streams"));

        List<Integer> primes = new ArrayList<>();

        // Return empty list if less than 2
        if (maxValue < 2) {
            return primes;
        }

        // Initialise the list with odd numbers only
        List<Integer> isPrimes = Collections.synchronizedList(
                IntStream.rangeClosed(3, maxValue).
                        filter(n -> n % 2 != 0).
                        boxed().collect(Collectors.toList()));

        primes.add(2);

        int maxValueToCheck = (int) Math.sqrt(maxValue);

        int i = 3;
        while (i <= maxValueToCheck) {

            final int currentPrime = i;
            List<Integer> newIsPrimes = Collections.synchronizedList(new ArrayList<>());

            // Using a parallel stream is not very performant. The issue is getting the next value
            // in the list which requires the result to be ordered or a min value retrieved
            isPrimes.parallelStream().
//            isPrimes.stream().
                    filter(n -> n % currentPrime != 0).
                    forEach(newIsPrimes::add);
//                    forEachOrdered(newIsPrimes::add);

            primes.add(currentPrime);
            isPrimes = newIsPrimes;

            // option 1 when the result isPrimes is sorted
//            i = isPrimes.get(0);

            // options 2, 3, 4, 5 when the result isPrimes is not sorted. Uncomment one option
            i = isPrimes.parallelStream().mapToInt(n -> n).min().getAsInt();
//            i = isPrimes.parallelStream().min(Integer::compare).get();
//            i = isPrimes.parallelStream().reduce(Integer::min).get();
//            i = Collections.min(isPrimes);

            // option 6 when the result isPrimes is not sorted. Uncomment both lines
//            isPrimes.sort(Comparator.<Integer>naturalOrder());
//            i = isPrimes.get(0);
        }

        // Append the primes greater than maxValueToCheck
        primes.addAll(isPrimes.stream().sorted().collect(Collectors.toList()));

        return primes;
    }
}
