package Models.Exceptions;

public class CompleteTeamException extends Exception {
    public CompleteTeamException() {
        super("El equipo esta completo");
    }
}