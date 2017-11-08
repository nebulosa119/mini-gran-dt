package Models.Exceptions;

public class InsufficientFundsException extends Exception {
    private static final String MESSAGE = "Insufficient Funds";
    public InsufficientFundsException() {
        super(MESSAGE);
    }
}
