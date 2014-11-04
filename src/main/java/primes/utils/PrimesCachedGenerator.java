package primes.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import primes.cache.PrimesCache;
import primes.cache.PrimesGuavaCache;

import java.util.List;

public class PrimesCachedGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(PrimesCachedGenerator.class);

    private PrimesCache primesCache;
    private PrimesGenerator primesGenerator;

    public PrimesCachedGenerator(PrimesGenerator primesGenerator) {

        this.primesGenerator = primesGenerator;
        primesCache = new PrimesGuavaCache();
        logger.info(String.format("Initialized with Guava cache"));
    }

    public List<Integer> generate(int maxValue) {

        // Only use odd keys if maxValue greater than 3 to reduce cache size
        int key = (maxValue > 3 && maxValue % 2 == 0) ? maxValue - 1 : maxValue;

        List<Integer> primesList = primesCache.getIfPresent(key);

        if (primesList != null) {
            logger.info(String.format("Found in cache using key %,d", key));
        } else {
            logger.info(String.format("Not found in cache using key %,d", key));
            primesList = primesGenerator.generate(maxValue);
            primesCache.put(key, primesList);
        }

        return primesList;
    }
}
