package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa el equipo de futbol virtual que un DT haya decidido armar.
 */
public class DTTeam extends Team implements Serializable{

    private static final long serialVersionUID = 1L;

    private int points = 0;

    /**
     * Constructor
     * @param max_players Representa la cantidad máxima de jugadores que puede tener en este torneo.
     */
    DTTeam(int max_players) {
        super(max_players);
    }

    /**
     * Método para actualizar los puntos del usuario que el mismo recibe en función de este equipo.
     * @param p Las propiedades nuevas
     */
    void refreshPoints(PhysicalPlayer.Properties p) {
        points += p.getPoints();
    }

    /**
     * Método para obtener los puntos del usuario que el mismo tiene en función de este equipo.
     */
    int getUserPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof DTTeam))
            return false;
        DTTeam aux = (DTTeam) o;
        return super.equals(aux);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(points);
        out.writeObject(physicalPlayers);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        points = ois.readInt();
        physicalPlayers = (ArrayList<PhysicalPlayer>) ois.readObject();
    }
}
