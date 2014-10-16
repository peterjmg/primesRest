package primes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class DivisionStreamPrimesGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(DivisionStreamPrimesGenerator.class);


    public List<Integer> generate(int maxValue) {

        logger.info(String.format("Generating primes using division algorithm and IntStream)"));

        List<Integer> values = Collections.synchronizedList(new ArrayList<>());

        if (maxValue >= 2) {
            values.add(2);

            if (maxValue > 2) {
                IntStream.rangeClosed(3, maxValue).parallel().
                    filter(i -> (i % 2 != 0) && (IsOddNumberPrime(i))).
                    forEach(values::add);
            }
        }

        values.sort(Comparator.<Integer>naturalOrder());
        return values;
    }

    // This presumes number given is odd
    public static boolean IsOddNumberPrime(int value) {

        boolean primeNo = true;

        if (value < 3) {
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


    /*
    public List<Integer> generate(int maxValue) {

        logger.info(String.format("Generating primes using division algorithm)"));

        List<Integer> values = new ArrayList<Integer>();

        if (maxValue >= 2) {
            values.add(2);

            // Only need to check odd numbers after 2
            for (int value = 3; value <= maxValue; value += 2) {
                if (IsPrime(value)) {
                    values.add(value);
                }
            }
        }

        return values;
    }

    public static boolean IsPrime(int value) {

        boolean primeNo = true;

        if (value < 2) {
            primeNo = false;
        } else if ((value > 2) && (value % 2 == 0)) {
            primeNo = false;
        } else {
            for (int i = 3; primeNo && i * i <= value; i += 2) {
                if (value % i == 0) {
                    primeNo = false;
                }
            }
        }

        return primeNo;
    }

        */
}