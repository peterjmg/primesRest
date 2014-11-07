package primes.utils;

import primes.exception.InvalidOptionException;

public class PrimesGeneratorFactory {

    public static final String ALG_DIVISION = "D";
    public static final String ALG_SIEVE = "S";
    public static final String ALG_DIVISION_STREAM = "DS";
    public static final String ALG_SIEVE_STREAM = "SS";
    public static final String ALG_SIEVE_PARALLEL = "SP";

    private static PrimesGenerator sievePrimesGenerator = new SievePrimesGenerator();
    private static PrimesGenerator divisionPrimesGenerator = new DivisionPrimesGenerator();
    private static PrimesGenerator divisionStreamPrimesGenerator = new DivisionStreamPrimesGenerator();
    private static PrimesGenerator sieveStreamPrimesGenerator = new SieveStreamPrimesGenerator();
    private static PrimesGenerator sieveStreamParallelPrimesGenerator = new SieveStreamParallelPrimesGenerator();

    private static PrimesGenerator sievePrimesCachedGenerator =
            new PrimesCachedGenerator(new SievePrimesGenerator());
    private static PrimesGenerator divisionPrimesCachedGenerator =
            new PrimesCachedGenerator(new DivisionPrimesGenerator());
    private static PrimesGenerator divisionStreamPrimesCachedGenerator =
            new PrimesCachedGenerator(new DivisionStreamPrimesGenerator());
    private static PrimesGenerator sieveStreamPrimesCachedGenerator =
            new PrimesCachedGenerator(new SieveStreamPrimesGenerator());
    private static PrimesGenerator sieveStreamParallelPrimesCachedGenerator =
            new PrimesCachedGenerator(new SieveStreamParallelPrimesGenerator());

    public static PrimesGenerator getPrimesGenerator(String option)
            throws InvalidOptionException
    {
        PrimesGenerator primesGenerator;

        if (option == null) {
            primesGenerator = divisionPrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_SIEVE)) {
            primesGenerator = sievePrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_DIVISION_STREAM)) {
            primesGenerator = divisionStreamPrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_SIEVE_STREAM)) {
            primesGenerator = sieveStreamPrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_SIEVE_PARALLEL)) {
            primesGenerator = sieveStreamParallelPrimesGenerator;
        } else if (option.equalsIgnoreCase(ALG_DIVISION)) {
            primesGenerator = divisionPrimesGenerator;
        } else {
            throw new InvalidOptionException(option);
        }

        return primesGenerator;
    }

    public static PrimesGenerator getPrimesCachedGenerator(String option)
            throws InvalidOptionException
    {
        PrimesGenerator primesGenerator;

        if (option == null) {
            primesGenerator = divisionPrimesCachedGenerator;
        } else if (option.equalsIgnoreCase(ALG_SIEVE)) {
            primesGenerator = sievePrimesCachedGenerator;
        } else if (option.equalsIgnoreCase(ALG_DIVISION_STREAM)) {
            primesGenerator = divisionStreamPrimesCachedGenerator;
        } else if (option.equalsIgnoreCase(ALG_SIEVE_STREAM)) {
            primesGenerator = sieveStreamPrimesCachedGenerator;
        } else if (option.equalsIgnoreCase(ALG_SIEVE_PARALLEL)) {
            primesGenerator = sieveStreamParallelPrimesCachedGenerator;
        } else if (option.equalsIgnoreCase(ALG_DIVISION)) {
            primesGenerator = divisionPrimesCachedGenerator;
        } else {
            throw new InvalidOptionException(option);
        }

        return primesGenerator;
    }
}
