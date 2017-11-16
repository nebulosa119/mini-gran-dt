package Models.Exceptions;

/**
 * Excepción usada cuando un DT no tiene fondos para agregar un jugador.
 */
public class InsufficientFundsException extends Exception{
    public InsufficientFundsException() {
        super("Fondos insuficientes.");
    }
}
