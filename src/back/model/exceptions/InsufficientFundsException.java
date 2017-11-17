package back.model.exceptions;

/**
 * Excepción usada cuando un DT no tiene fondos para agregar un jugador.
 */
public class InsufficientFundsException extends Exception{
    public InsufficientFundsException() {
        super("Fondos insuficientes.");
    }
}
