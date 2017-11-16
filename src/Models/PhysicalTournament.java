package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Modela un toreno fisico, con equipos reales y jugadores reales, es administrador
 * por el administrador
 */
public class PhysicalTournament implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<PhysicalTeam> teams;
    private Administrator administrator;
    private String name;
    private int maxPlayers;

    public PhysicalTournament(String tourName) {
        this(tourName,0);
    }

    public PhysicalTournament(String name, int maxPlayers) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        teams = new ArrayList<>();
    }

    PhysicalTournament(PhysicalTournament t) {
        this(t.getName(),t.getMaxPlayers());
        teams.addAll(t.getTeams());
    }
    /**
     * @return un array con los equipos dentro de este torneo*/
    public ArrayList<PhysicalTeam> getTeams() {
        return new ArrayList<>(teams);
    }
    /**
     * @param teamName el nombre del equipo qeu se busca
     * @return el equipo buscado*/
    public PhysicalTeam getTeam(String teamName){
        for(PhysicalTeam team : teams)
            if(team.getName().equals(teamName))
                return team;
        return null;
    }
    /**
     * @return nombre del torneo*/
    public String getName() {
        return name;
    }
    /**
     * @return entero con la maxima cantidad de jugadores en este torneo*/
    public int getMaxPlayers() {
        return maxPlayers;
    }
    /**
     * Agrega un equipo nuevo al torneo
     * @param t el nuevo equipo
     * */
    public void addTeam(PhysicalTeam t) {
        teams.add(new PhysicalTeam(t.getName(), t, maxPlayers));
    }
    /**
     * @param t el equipo a buscar
     * @return true si el contiene al equipo que se busca, false en otro caso*/
    public boolean hasTeam(PhysicalTeam t) {
        for(PhysicalTeam team : teams) {
            if(t.equals(team))
                return true;
        }
        return false;
    }
    /**
     * Se requiere saber quien es quien adminstra este torneo
     * @param administrator de este torneo
     * */
    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
    /**
     * Transifiere la inoracion nueva subia por el adminsitrador a los equipos del torneo
     * @param dataTeams informacion ed ada equipo
     * */
    void refresh(Map<String, Map<String, PhysicalPlayer.Properties>> dataTeams) {
        for (PhysicalTeam myTeam : teams) {
            if(dataTeams.get(myTeam.getName()) != null)
                myTeam.refresh(dataTeams.get(myTeam.getName()));
        }
    }
    /**
     * @return Ranking de un determinado jugador fisico.
     * @param physicalPlayer Jugador fisico.
     * */
    public int getRanking(PhysicalPlayer physicalPlayer) {
        Map<PhysicalPlayer,Integer> players = unifyPlayers();
        return players.get(physicalPlayer);
    }
    /**
     * Se requiere unificar todos los jugadores para ser accedidos
     */
    private Map<PhysicalPlayer,Integer> unifyPlayers() {
        ArrayList<PhysicalPlayer> physicalPlayers = new ArrayList<>();
        for (PhysicalTeam team : teams) {
            physicalPlayers.addAll(team.getPhysicalPlayers());
        }
        Map<PhysicalPlayer,Integer> map = new HashMap<>();
        physicalPlayers.sort((p1, p2) -> p2.getPoints() - p1.getPoints());
        for (int i = 0; i< physicalPlayers.size(); i++)
            map.put(physicalPlayers.get(i),i+1);
        return map;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof PhysicalTournament))
            return false;
        PhysicalTournament tour = (PhysicalTournament) obj;
        return name.equals(tour.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode()*2 : 0;
    }

    @Override
    public String toString() {
        return name +"{"+ Arrays.toString(teams.toArray()) +"}";
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeInt(maxPlayers);
        out.writeObject(teams);
    }

    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        maxPlayers = ois.readInt();
        teams = (ArrayList<PhysicalTeam>) ois.readObject();
    }

    public Administrator getAdministrator() {
        return administrator;
    }
}
