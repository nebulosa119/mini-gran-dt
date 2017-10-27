package Model;

import java.util.Vector;

public class Tournament extends Identifiable {
    private Vector<Team> teams;
    // para llevar registro de los jugadores
    //private HashMap<Date,> por simplificaicon no vamos a llevar(por ahora) registro de las fechas, eso
    // se opcupara el adiministrador en su pagina web.....

    private static int IDCounter = 0;

    public Tournament(String name, int id) {
        super(name, id);
    }

    public Tournament(String name){
        super(name,IDCounter++);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Tournament))
            return false;
        Tournament tournament = (Tournament) o;
        return super.equals(tournament);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 7; // para diferenciarlo d
        return result;
    }

}
