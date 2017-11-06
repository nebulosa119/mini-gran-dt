package Models.exceptions;

///Excepcion para cuando un usuario quiere cambiar jugadores en el equipo pero se  agotaron los cambios

public class NoChangesLeftException extends Exception{
    public NoChangesLeftException(){super();}
}
