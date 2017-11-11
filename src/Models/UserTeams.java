package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserTeams {

    private Map<Tournament, UserTeam> teams = new HashMap<>();
    private User u;

    public UserTeams(User u) {
        this.u = u;
    }

    public void addNewTeam(Tournament t) {
        teams.put(t, new UserTeam(u));
    }

    public void removePlayer(Tournament t, Player p) {
        teams.get(t).removePlayer(p);
    }

    public void addPlayer(Tournament t, Player p) {
        teams.get(t).addPlayer(p);
    }

    public boolean isParticipating(Tournament t) {
        return teams.containsKey(t);
    }

    public ArrayList<Player> getUserTeamPlayers(Tournament t) {
        return teams.get(t).getPlayers();
    }
}
