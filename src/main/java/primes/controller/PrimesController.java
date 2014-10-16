package primes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import primes.domain.Primes;
import primes.utils.PrimesGeneratorFactory;
import primes.utils.PrimesGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/primes")
public class PrimesController {

    static final Logger logger = LoggerFactory.getLogger(PrimesController.class);

    /* Can produce both json or xml responses */
    @RequestMapping(value = "/{maxValue}", method = RequestMethod.GET,
            produces={"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Primes primes(@PathVariable("maxValue") int maxValue,
            @RequestParam(value = "opt", required = false,
                    defaultValue = PrimesGeneratorFactory.ALG_DIVISION) String opt) {

        logger.info(String.format("Generating primes with max value %,d using option %s)", maxValue, opt));

        List<Integer> values;

        PrimesGenerator primesGenerator = PrimesGeneratorFactory.getPrimesGenerator(opt);

        long startTime, endTime = 0;
        startTime = System.currentTimeMillis();

        values = primesGenerator.generate(maxValue);

        endTime = System.currentTimeMillis();

        logger.info(String.format("Generated %,d primes with max value %,d, in %,d ms)",
                values.size(), maxValue, endTime - startTime));

        return new Primes(maxValue, values);
	}
}
