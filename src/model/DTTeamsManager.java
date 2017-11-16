package model;

import model.exceptions.CompleteTeamException;
import model.exceptions.ExistentNameException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de administración de equipos del DT.
 * Cada DT tiene un equipo por torneo.
 */
public class DTTeamsManager implements Serializable{

    private static final long serialVersionUID = 1L;

    private Map<PhysicalTournament, DTTeam> teams = new HashMap<>();

    /**
     * Método para registrar la creación y uso de un equipo DTTeam en un torneo particular.
     * @param t
     */
    void addNewTeam(PhysicalTournament t) {
        teams.put(t, new DTTeam(t.getMaxPlayers()));
    }

    /**
     * Método para remover un jugador comprado de un equipo DTTeam en un torneo en particular.
     * @param t
     * @param p
     */
    void removePlayer(PhysicalTournament t, PhysicalPlayer p) {
        teams.get(t).removePlayer(p);
    }

    /**
     * Método para agregar un jugador de un torneo en particular a un equipo según el torneo.
     * @param t
     * @param p
     */
    void addPlayer(PhysicalTournament t, PhysicalPlayer p) throws CompleteTeamException, ExistentNameException {
        teams.get(t).addPlayer(p);
    }

    /**
     * Método para determinar si el usuario se encuentra participando de un torneo.
     * @param t
     * @return
     */
    boolean isParticipating(PhysicalTournament t) {
        return teams.containsKey(t);
    }

    /**
     * Método para conseguir una lista con los integrantes de un equipo DTTeam en un torneo en particular.
     * @param t
     * @return
     */
    public ArrayList<PhysicalPlayer> getUserTeamPlayers(PhysicalTournament t) {
        return teams.get(t).getPhysicalPlayers();
    }

    /**
     * Método para conseguir los puntos de un usuario en un torneo particular.
     * @param t
     * @return
     */
    int getUserPoints(PhysicalTournament t) {
        if (teams.get(t)==null)
            return 0;
        return teams.get(t).getUserPoints();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(teams);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        teams = (Map<PhysicalTournament, DTTeam>) ois.readObject();
    }

    /**
     * Método para actualizar los puntos del usuario en función de los cambios hechos a los jugadores que componen los equipos DTTeam del usuario.
     * @param propertiesMap
     * @param tour
     */
    void refreshPoints(Map<String, PhysicalPlayer.Properties> propertiesMap, PhysicalTournament tour) {
        DTTeam t = teams.get(tour);
        for(PhysicalPlayer p : t.getPhysicalPlayers()) {
            for(String name : propertiesMap.keySet()) {
                if(p.getName().equals(name)) {
                    t.refreshPoints(propertiesMap.get(p.getName()));
                }
            }
        }
    }

}
