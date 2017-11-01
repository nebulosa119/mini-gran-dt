package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    public void refreshTeam(HashMap<String, Properties> prop, Team t) {
        for(Team team: teams) {
            if(team.equals(t)) {
                team.refreshPlayers(prop);
            }
        }
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

    public void refresh(Tournament dataTour) {
        for (Team myTeam:teams) {
            for (Team dataTeam:dataTour.getTeams()) {
                if (myTeam.getName().equals(dataTeam.getName())){
                    myTeam.refresh(dataTeam);
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return name +"{"+ Arrays.toString(teams.toArray()) +"}";
    }
}
