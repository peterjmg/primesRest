package primes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import primes.domain.Primes;
import primes.exception.PrimesException;
import primes.utils.PrimesGeneratorFactory;
import primes.utils.PrimesGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/primes")
public class PrimesController {

    public static final String OPTION_PARAMETER = "opt";

    static final Logger logger = LoggerFactory.getLogger(PrimesController.class);

    @Value("${Cache}")
    private boolean useCache;

    /* Can produce both json or xml responses */
    @RequestMapping(value = "/{maxValue}", method = RequestMethod.GET,
            produces={"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Primes primes(@PathVariable("maxValue") int maxValue,
            @RequestParam(value = OPTION_PARAMETER, required = false,
                    defaultValue = PrimesGeneratorFactory.ALG_DIVISION) String opt)
            throws PrimesException {

        logger.info(String.format("Generating primes with max value %,d using option %s, " +
                "and useCache %b", maxValue, opt, useCache));

        List<Integer> values;

        PrimesGenerator primesGenerator;
        if (useCache) {
            primesGenerator = PrimesGeneratorFactory.getPrimesCachedGenerator(opt);
        } else {
            primesGenerator = PrimesGeneratorFactory.getPrimesGenerator(opt);
        }

        long startTime, endTime;
        startTime = System.currentTimeMillis();

        values = primesGenerator.generate(maxValue);

        endTime = System.currentTimeMillis();

        logger.info(String.format("Generated %,d primes with max value %,d, in %,d ms",
                values.size(), maxValue, endTime - startTime));

        return new Primes(maxValue, values);
	}
}
