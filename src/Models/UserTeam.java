package Models;

import java.util.ArrayList;

class UserTeam {

    private int points = 0;
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

    public void refreshPoints(Player.Properties p) {
        points += p.getPoints();
    }

}
