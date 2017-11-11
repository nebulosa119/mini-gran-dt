package Models.Exceptions;

public class InsufficientFundsException extends RuntimeException{
    private static final String MESSAGE = "Insufficient Funds";
    public InsufficientFundsException() {
        super(MESSAGE);
    }
}
