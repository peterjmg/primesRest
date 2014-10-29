package primes.utils;

import primes.exception.InvalidOptionException;

public class PrimesGeneratorFactory {

    public static final String ALG_DIVISION = "D";
    public static final String ALG_SIEVE = "S";
    public static final String ALG_DIVISION_CACHED = "DC";
    public static final String ALG_DIVISION_STREAM = "DS";

    private static SievePrimesGenerator sievePrimesGenerator = new SievePrimesGenerator();
    private static DivisionPrimesGenerator divisionPrimesGenerator = new DivisionPrimesGenerator();
    private static DivisionCachedPrimesGenerator divisionCachedPrimesGenerator = new DivisionCachedPrimesGenerator();
    private static DivisionStreamPrimesGenerator divisionStreamPrimesGenerator = new DivisionStreamPrimesGenerator();

    public static PrimesGenerator getPrimesGenerator(String option)
            throws InvalidOptionException
    {
        PrimesGenerator primesGenerator;

        if (option == null) {
            primesGenerator = divisionPrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_SIEVE)) {
            primesGenerator = sievePrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_DIVISION_CACHED)) {
            primesGenerator = divisionCachedPrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_DIVISION_STREAM)) {
            primesGenerator = divisionStreamPrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_DIVISION)) {
            primesGenerator = divisionPrimesGenerator;
        } else {
            throw new InvalidOptionException(option);
        }

        return primesGenerator;
    }
}
