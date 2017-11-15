package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de administración de equipos de usuarios.
 */
public class UserTeams implements Serializable{

    private static final long serialVersionUID = 1L;

    private Map<Tournament, UserTeam> teams = new HashMap<>();

    /**
     * Método para registrar la creación y uso de un equipo UserTeam en un torneo particular.
     * @param t
     */
    void addNewTeam(Tournament t) {
        teams.put(t, new UserTeam(t.getMaxPlayers()));
    }

    /**
     * Método para remover un jugador comprado de un equipo UserTeam en un torneo en particular.
     * @param t
     * @param p
     */
    void removePlayer(Tournament t, Player p) {
        teams.get(t).removePlayer(p);
    }

    /**
     * Método para agregar un jugador de un torneo en particular a un equipo según el torneo.
     * @param t
     * @param p
     */
    void addPlayer(Tournament t, Player p) {
        teams.get(t).addPlayer(p);
    }

    /**
     * Método para determinar si el usuario se encuentra participando de un torneo.
     * @param t
     * @return
     */
    boolean isParticipating(Tournament t) {
        return teams.containsKey(t);
    }

    /**
     * Método para conseguir una lista con los integrantes de un equipo UserTeam en un torneo en particular.
     * @param t
     * @return
     */
    public ArrayList<Player> getUserTeamPlayers(Tournament t) {
        return teams.get(t).getPlayers();
    }

    /**
     * Método para conseguir los puntos de un usuario en un torneo particular.
     * @param t
     * @return
     */
    int getUserPoints(Tournament t) {
        return teams.get(t).getUserPoints();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(teams);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        teams = (Map<Tournament, UserTeam>) ois.readObject();
    }

    /**
     * Método para actualizar los puntos del usuario en función de los cambios hechos a los jugadores que componen los equipos UserTeam del usuario.
     * @param propertiesMap
     * @param tour
     */
    void refreshPoints(Map<String, Player.Properties> propertiesMap, Tournament tour) {
        UserTeam t = teams.get(tour);
        for(Player p : t.getPlayers()) {
            for(String name : propertiesMap.keySet()) {
                if(p.getName().equals(name)) {
                    t.refreshPoints(propertiesMap.get(p.getName()));
                }
            }
        }
    }

}
