package primes.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;

public class PrimesGuavaCache implements PrimesCache {

    Cache<Integer, List<Integer>> cache = CacheBuilder.newBuilder().
            maximumSize(1000).recordStats().build();

    @Override
    public List<Integer> getIfPresent(int key) {

        return cache.getIfPresent(key);
    }

    @Override
    public void put(int key, List<Integer> primesList) {

        cache.put(key, primesList);
    }
}
