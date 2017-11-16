package Models.Exceptions;

/**
 * Excepción usada cuando a un equipo completo se le intenta agregar un jugador.
 */
public class CompleteTeamException extends Exception {
    public CompleteTeamException() {
        super("El equipo esta completo");
    }
}