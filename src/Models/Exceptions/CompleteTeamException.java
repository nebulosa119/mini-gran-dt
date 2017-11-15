package Models.Exceptions;

public class CompleteTeamException extends RuntimeException{
    public CompleteTeamException() {
            super("El equipo esta completo");
        }
}