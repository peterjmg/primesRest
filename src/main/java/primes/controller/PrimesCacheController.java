package primes.controller;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheStats;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import primes.cache.PrimesGuavaCache;
import primes.domain.Primes;
import primes.domain.PrimesCacheStats;
import primes.exception.PrimesException;
import primes.utils.PrimesGeneratorFactory;
import primes.utils.PrimesGuavaCacheFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/primesCache")
public class PrimesCacheController {

    public static final String OPTION_PARAMETER = "opt";

    static final Logger logger = LoggerFactory.getLogger(PrimesCacheController.class);

    /* Can produce both json or xml responses */
    @RequestMapping(value = "/{maxValue}", method = RequestMethod.GET,
            produces={"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Primes primes(@PathVariable("maxValue") int maxValue,
                         @RequestParam(value = OPTION_PARAMETER, required = false,
                                 defaultValue = PrimesGeneratorFactory.ALG_DIVISION) String opt)
            throws PrimesException {

        logger.info(String.format("Generating primes from cache with max value %,d using option %s)", maxValue, opt));

        Primes primes;
            PrimesGuavaCache primesGuavaCache = PrimesGuavaCacheFactory.getCache(opt);

        try {
            long startTime, endTime;
            startTime = System.currentTimeMillis();

            primes = primesGuavaCache.get(maxValue);

            endTime = System.currentTimeMillis();

            logger.info(String.format("Retrieved from cache %,d primes with max value %,d, in %,d ms)",
                    primes.getPrimes().size(), maxValue, endTime - startTime));

        } catch (ExecutionException e) {
            throw new PrimesException(e.getCause().toString());
        }

        return primes;
    }

    /* Always a json response */
    @RequestMapping(value = "cacheStats", method = RequestMethod.GET,
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public PrimesCacheStats primesCacheStats(@RequestParam(value = OPTION_PARAMETER, required = false,
                                 defaultValue = PrimesGeneratorFactory.ALG_DIVISION) String opt)
            throws PrimesException {

        logger.info(String.format("Getting stats for cache using option %s)", opt));

        PrimesGuavaCache primesGuavaCache = PrimesGuavaCacheFactory.getCache(opt);

        PrimesCacheStats primesCacheStats = primesGuavaCache.getCacheStats();

        logger.info(String.format("Returning stats %s)", primesCacheStats.toString()));

        return primesCacheStats;
    }
}
