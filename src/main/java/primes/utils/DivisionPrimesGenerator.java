package primes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DivisionPrimesGenerator implements PrimesGenerator {

    static final Logger logger = LoggerFactory.getLogger(DivisionPrimesGenerator.class);

    public List<Integer> generate(int maxValue) {

        logger.info(String.format("Generating primes using division algorithm"));

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
            for (int i = 3; primeNo && i * i  <= value; i += 2) {
                if (value % i == 0) {
                    primeNo = false;
                }
            }
        }

        return primeNo;
    }
}