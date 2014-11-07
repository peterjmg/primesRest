package primes.utils;

import primes.cache.PrimesGuavaLoadingCache;
import primes.exception.InvalidOptionException;

public class PrimesGuavaCacheFactory {

    private static PrimesGuavaLoadingCache divisionPrimesGuavaLoadingCache =
            (new PrimesGuavaLoadingCache(new DivisionPrimesGenerator()));
    private static PrimesGuavaLoadingCache sievePrimesGuavaLoadingCache =
            (new PrimesGuavaLoadingCache(new SievePrimesGenerator()));
    private static PrimesGuavaLoadingCache divisionStreamPrimesGuavaLoadingCache =
            (new PrimesGuavaLoadingCache(new DivisionStreamPrimesGenerator()));
    private static PrimesGuavaLoadingCache sieveStreamPrimesGuavaLoadingCache =
            (new PrimesGuavaLoadingCache(new SieveStreamPrimesGenerator()));
    private static PrimesGuavaLoadingCache sieveStreamParallelPrimesGuavaLoadingCache =
            (new PrimesGuavaLoadingCache(new SieveStreamParallelPrimesGenerator()));

    public static PrimesGuavaLoadingCache getCache(String option)
            throws InvalidOptionException
    {
        PrimesGuavaLoadingCache primesGuavaLoadingCache;

        if (option == null) {
            primesGuavaLoadingCache = divisionPrimesGuavaLoadingCache;
        } else if (option.equalsIgnoreCase(PrimesGeneratorFactory.ALG_SIEVE)) {
            primesGuavaLoadingCache = sievePrimesGuavaLoadingCache;
        } else if (option.equalsIgnoreCase(PrimesGeneratorFactory.ALG_DIVISION_STREAM)) {
            primesGuavaLoadingCache = divisionStreamPrimesGuavaLoadingCache;
        } else if (option.equalsIgnoreCase(PrimesGeneratorFactory.ALG_SIEVE_STREAM)) {
            primesGuavaLoadingCache = sieveStreamPrimesGuavaLoadingCache;
        } else if (option.equalsIgnoreCase(PrimesGeneratorFactory.ALG_SIEVE_PARALLEL)) {
            primesGuavaLoadingCache = sieveStreamParallelPrimesGuavaLoadingCache;
        } else if (option.equalsIgnoreCase(PrimesGeneratorFactory.ALG_DIVISION)) {
            primesGuavaLoadingCache = divisionPrimesGuavaLoadingCache;
        } else {
            throw new InvalidOptionException(option);
        }

        return primesGuavaLoadingCache;
    }
}
