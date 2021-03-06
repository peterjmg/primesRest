package primes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DivisionStreamPrimesGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(DivisionStreamPrimesGenerator.class);

    public List<Integer> generate(int maxValue) {

        logger.info(String.format("Generating primes using division algorithm and IntStream"));

        return IntStream.rangeClosed(2, maxValue).
                parallel().
                filter(DivisionStreamPrimesGenerator::IsPrime).
                sorted().
                boxed().collect(Collectors.toList());
    }

    public List<Integer> generateAlt(int maxValue) {

        logger.info(String.format("Generating primes using division algorithm and IntStream"));

        int rangeBottom = maxValue <= 2 ? 2 : 1;
        int rangeTop = maxValue == 2 ? 2 :
                (int)Math.ceil(maxValue / 2.0);

        // Only select 2 and odd numbers over 2 to pass to filter
        return IntStream.rangeClosed(rangeBottom, rangeTop).
                map(i -> i == 2 ? 2  : 2 * i + 1).
                parallel().
                filter(DivisionStreamPrimesGenerator::IsPrime).
                sorted().
                boxed().collect(Collectors.toList());
    }

    public static boolean IsPrime(int value) {

        boolean primeNo = true;

        if (value < 2) {
            primeNo = false;
        } else if (value == 2) {
            primeNo = true;
        } else if (value % 2 == 0) {
            primeNo = false;
        } else {
            for (int i = 3; primeNo && i * i  <= value; i += 2) {
                if (value % i == 0) {
                    primeNo = false;
                }
            }
        }

        return primeNo;
    }
}