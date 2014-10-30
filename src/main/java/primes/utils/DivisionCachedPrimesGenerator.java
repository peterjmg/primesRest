package primes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Uses the sieve of Eratosthenes to determine the prime numbers
 */
public class DivisionCachedPrimesGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(DivisionCachedPrimesGenerator.class);

    private static List<Integer> primesCache = new ArrayList<Integer>();
    private static int cacheMaxValue = 0;

    public List<Integer> generate(int maxValue) {
        logger.info(String.format("Generating primes using Division algorithm and a cache)"));

        List<Integer> values;
        int maxValueOfCacheCopy;

        // Need to sync
        synchronized(this)
        {
            // Take a copy of the cache
            values = new ArrayList<>(primesCache);
            maxValueOfCacheCopy = cacheMaxValue;
        }

        // If maxValue == maxValueOfCacheCopy we need to do nothing just return the copy

        // If a subset of the cache is required.
        if (maxValue < maxValueOfCacheCopy)
        {
            logger.info(String.format("Generating subset of cache"));

            int maxIndexInCache = -1;
            for (int valueInCache = maxValue; valueInCache > 1 && maxIndexInCache < 0; valueInCache--)
            {
                if (values.contains(valueInCache)) {
                    maxIndexInCache = values.indexOf(valueInCache);
                }
            }

            if (maxIndexInCache >= 0)
            {
                values = new ArrayList<Integer>(values.subList(0, maxIndexInCache + 1));
            }
            else
            {
                values = new ArrayList<>();
            }
        }
        // The cache need to be extended
        else if (maxValue > maxValueOfCacheCopy)
        {
            logger.info(String.format("Updating cache"));

            UpdateCacheCopy(values, maxValueOfCacheCopy, maxValue);

            // Update the cache needs to be sync again
            synchronized(this)
            {
                // Only need to proceed if another thread has not already updated it further
                if (cacheMaxValue < maxValue)
                {
                    // Copy
                    primesCache = new ArrayList<>(values);
                    cacheMaxValue = maxValue;
                }
            }
        }

        return values;
    }

    private void UpdateCacheCopy(List<Integer> cacheCopyValues, int originalMaxValue, int newMaxValue)
    {
        int value = originalMaxValue + 1;

        // First time in 2 will need to be added
        if (originalMaxValue < 2)
        {
            cacheCopyValues.add(2);
            value = 3;
        }
        else if (value % 2 == 0) // Only need to check odd numbers so make sure start is odd
            value++;

        for (; value <= newMaxValue; value += 2)
        {
            if (DivisionPrimesGenerator.IsPrime(value))
                cacheCopyValues.add(value);
        }
    }
}
