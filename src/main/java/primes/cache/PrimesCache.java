package primes.cache;

import java.util.List;

public interface PrimesCache {

    public List<Integer> getIfPresent(int key);

    public void put(int key, List<Integer> primesList);
}
