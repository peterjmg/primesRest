package primes.utils;

import primes.cache.PrimesGuavaCache;
import primes.exception.InvalidOptionException;

public class PrimesGuavaCacheFactory {

    private static PrimesGuavaCache divisionPrimesGuavaCache =
            (new PrimesGuavaCache(new DivisionPrimesGenerator()));
    private static PrimesGuavaCache sievePrimesGuavaCache =
            (new PrimesGuavaCache(new SievePrimesGenerator()));

    public static PrimesGuavaCache getCache(String option)
            throws InvalidOptionException
    {
        PrimesGuavaCache primesGuavaCache;

        if (option == null) {
            primesGuavaCache = divisionPrimesGuavaCache;
        } else if (option.equalsIgnoreCase(PrimesGeneratorFactory.ALG_SIEVE)) {
            primesGuavaCache = sievePrimesGuavaCache;
        } else if (option.equalsIgnoreCase(PrimesGeneratorFactory.ALG_DIVISION)) {
            primesGuavaCache = divisionPrimesGuavaCache;
        } else {
            throw new InvalidOptionException(option);
        }

        return primesGuavaCache;
    }
}
