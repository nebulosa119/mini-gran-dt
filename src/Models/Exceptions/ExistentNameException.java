package Models.Exceptions;

/**
 * Excepción usada cuando se quiere crear o agregar un jugador existente.
 */
public class ExistentNameException extends Exception {
    public ExistentNameException() {
        super("Nombre ya existente.");
    }
}

