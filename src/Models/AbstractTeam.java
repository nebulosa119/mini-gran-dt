package Models;

import java.util.ArrayList;

public abstract class AbstractTeam {

    protected int max_players;
    protected ArrayList<Player> players = new ArrayList<>();

    public AbstractTeam(int max_players) {
        this.max_players = max_players;
    }

    public void addPlayer(Player p) {
        if(players.contains(p)) return;
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof AbstractTeam))
            return false;
        AbstractTeam aux = (AbstractTeam) o;
        if(max_players != aux.max_players)
            return false;
        for(Player p1 : players) {
            for(Player p2: aux.getPlayers())
                if(!p1.equals(p2))
                    return false;
        }
        return true;
    }

}
