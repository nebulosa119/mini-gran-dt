package Models;

import java.util.ArrayList;

class UserTeam {

    private ArrayList<Player> players = new ArrayList<>();

    void addPlayer(Player p) {
        players.add(p);
    }

    void removePlayer(Player p) {
        players.remove(p);
    }

    ArrayList<Player> getPlayers() {
        return players;
    }
}
