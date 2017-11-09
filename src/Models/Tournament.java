package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Team> teams;
    private String name;
    private int maxPlayers;

    public Tournament(String tourName) {
        this(tourName,0);
    }

    public Tournament(String name, int maxPlayers) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        teams = new ArrayList<>();
    }

    Tournament(Tournament t) {
        this(t.getName(),t.getMaxPlayers());
        teams.addAll(t.getTeams());
    }

    public Team getTeam(String teamName){
        for(Team team : teams)
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

    public ArrayList<Team> getTeams() {
        return new ArrayList<Team>(teams);
    }

    /**Para cuando se cree el torneo y los equipos*/
    public void addTeam(Team t) {
        teams.add(new Team(t,maxPlayers));
    }

    public boolean hasTeam(Team t) {
        for(Team team : teams) {
            if(t.equals(team))
                return true;
        }
        return false;
    }

    public void refresh(Map<String,Map<String, Player.Properties>> dataTeams) {
        for (Team myTeam : teams) {
            myTeam.refresh(dataTeams.get(myTeam.getName()));
        }
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
        teams = (ArrayList) ois.readObject();
    }
}
