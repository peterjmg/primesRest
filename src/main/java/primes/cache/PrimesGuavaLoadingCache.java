package primes.cache;

import java.util.concurrent.ExecutionException;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import primes.domain.Primes;
import primes.domain.PrimesCacheStats;
import primes.exception.PrimesException;
import primes.utils.PrimesGenerator;

public class PrimesGuavaLoadingCache {

    static final Logger logger = LoggerFactory.getLogger(PrimesGuavaLoadingCache.class);

    LoadingCache<Integer, Primes> cache;

    public PrimesGuavaLoadingCache(PrimesGenerator primesGenerator) {

        init(primesGenerator);
    }

    private void init(PrimesGenerator primesGenerator) {

        CacheLoader<Integer, Primes> loader = new CacheLoader<Integer, Primes>() {

            public Primes load(Integer maxValue) throws PrimesException {

                logger.info(String.format("Using cache loader for maxValue %,d)", maxValue));
                return new Primes(maxValue, primesGenerator.generate(maxValue));
            }
        };

        cache = CacheBuilder.newBuilder().
                maximumSize(1000).
                recordStats().
                build(loader);
    }

    public Primes get(int key) throws ExecutionException {

        Primes primes = cache.get(key);

        return primes;
    }

    public PrimesCacheStats getCacheStats() {

        CacheStats cacheStats = cache.stats();

        return new PrimesCacheStats(cacheStats.hitCount(), cacheStats.missCount(),
                cacheStats.requestCount(), cache.size());
    }
}
