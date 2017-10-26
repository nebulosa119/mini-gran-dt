package clases;

import java.util.Vector;

public class Torneo extends Identificable {
    private Vector<Equipo> equipos; // para llevar registro de los jugadores
    //private HashMap<Date,> por simplificaicon no vamos a llevar(por ahora) registro de las fechas, eso
    // se opcupara el adiministrador en su pagina web.....

    private static int IDCounter = 0;

    public Torneo(String nombre, int id) {
        super(nombre, id);
    }

    public Torneo(String nombre){
        super(nombre,IDCounter++);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Torneo))
            return false;
        Torneo torneo = (Torneo) o;
        return super.equals(torneo);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 7; // para diferenciarlo d
        return result;
    }

}
