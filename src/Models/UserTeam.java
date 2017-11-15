package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa el equipo que un usuario haya decidido armar
 */
public class UserTeam extends AbstractTeam implements Serializable{

    private static final long serialVersionUID = 1L;

    private int points = 0;

    /**
     * Constructor
     * @param max_players Representa la cantidad máxima de jugadores que puede tener en este torneo.
     */
    UserTeam(int max_players) {
        super(max_players);
    }

    /**
     * Método para actualizar los puntos del usuario que el mismo recibe en función de este equipo
     * @param p Las propiedades nuevas
     */
    void refreshPoints(Player.Properties p) {
        points += p.getPoints();
    }

    /**
     * Método para obtener los puntos del usuario que el mismo tiene en función de este equipo
     */
    int getUserPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof UserTeam))
            return false;
        UserTeam aux = (UserTeam) o;
        return super.equals(aux);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(points);
        out.writeObject(players);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        points = ois.readInt();
        players= (ArrayList<Player>) ois.readObject();
    }
}
