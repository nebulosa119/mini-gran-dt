package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

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

    public ArrayList<PhysicalTeam> getTeams() {
        return new ArrayList<>(teams);
    }

    public PhysicalTeam getTeam(String teamName){
        for(PhysicalTeam team : teams)
            if(team.getName().equals(teamName))
                return team;
        return null;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**Para cuando se cree el torneo y los equipos*/
    public void addTeam(PhysicalTeam t) {
        teams.add(new PhysicalTeam(t.getName(), t, maxPlayers));
    }

    public boolean hasTeam(PhysicalTeam t) {
        for(PhysicalTeam team : teams) {
            if(t.equals(team))
                return true;
        }
        return false;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    void refresh(Map<String, Map<String, PhysicalPlayer.Properties>> dataTeams) {
        for (PhysicalTeam myTeam : teams) {
            if(dataTeams.get(myTeam.getName()) != null)
                myTeam.refresh(dataTeams.get(myTeam.getName()));
        }
    }

    public int getRanking(PhysicalPlayer physicalPlayer) {
        Map<PhysicalPlayer,Integer> players = unifyPlayers();
        return players.get(physicalPlayer);
    }

    private Map<PhysicalPlayer,Integer> unifyPlayers() {
        ArrayList<PhysicalPlayer> physicalPlayers = new ArrayList<>();
        for (PhysicalTeam team : teams) {
            physicalPlayers.addAll(team.getPhysicalPlayers());
        }
        Map<PhysicalPlayer,Integer> map = new HashMap<>();
        physicalPlayers.sort(new Comparator<PhysicalPlayer>() {
            @Override
            public int compare(PhysicalPlayer physicalPlayer, PhysicalPlayer t1) {
                return t1.getPoints() - physicalPlayer.getPoints();
            }
        });
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
