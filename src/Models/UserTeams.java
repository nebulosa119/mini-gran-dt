package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserTeams {

    private Map<Tournament, UserTeam> teams = new HashMap<>();

    void addNewTeam(Tournament t) {
        teams.put(t, new UserTeam());
    }

    void removePlayer(Tournament t, Player p) {
        teams.get(t).removePlayer(p);
    }

    void addPlayer(Tournament t, Player p) {
        teams.get(t).addPlayer(p);
    }

    boolean isParticipating(Tournament t) {
        return teams.containsKey(t);
    }

    public ArrayList<Player> getUserTeamPlayers(Tournament t) {
        return teams.get(t).getPlayers();
    }

    public void refresh(Player.Properties p, Tournament t) {
        teams.get(t).refreshPoints(p);
    }
}
