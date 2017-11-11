package Models;

import java.util.ArrayList;
import java.util.Set;

public class UserTeam {

    private User u;
    private ArrayList<Player> players = new ArrayList<>();

    public UserTeam(User u) {
        this.u = u;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
