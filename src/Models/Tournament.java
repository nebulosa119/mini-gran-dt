package Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Tournament extends Identifiable {

    private ArrayList<Team> teams;
    private int maxPlayers;

    public Tournament(String tourName) {
        super(tourName);
        teams = new ArrayList<Team>();
    }

    public Tournament(String name, int maxPlayers) {
        this(name);
        this.maxPlayers = maxPlayers;
    }

    Tournament(Tournament t) {
        this(t.getName(),t.getMaxPlayers());
        teams.addAll(t.getTeams());
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public ArrayList<Team> getTeams() {
        return new ArrayList<Team>(teams);
    }

    /**Para cuando se cree el torneo y los equipos*/
    public void addTeam(Team t) {
        teams.add(new Team(t));
    }

    public boolean hasTeam(Team t) {
        for(Team team : teams) {
            if(t.equals(team))
                return true;
        }
        return false;
    }

    public void refresh(Map<String,Map<String,Properties>> dataTeams) {
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
}
