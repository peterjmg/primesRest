package primes.exception;

public class InvalidOptionException extends PrimesException {

    private static final long serialVersionUID = -1001445739845119582L;

    public InvalidOptionException(String option) {
        super("Invalid option: " + option);
    }
}
