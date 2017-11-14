package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractTeam implements Serializable{

    int max_players;
    ArrayList<Player> players = new ArrayList<>();

    AbstractTeam(int max_players) {
        this.max_players = max_players;
    }

    void addPlayer(Player p) {
        if (!players.contains(p))
            players.add(p);
    }

    void removePlayer(Player p) {
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

    private void writeObject(ObjectOutputStream out)throws IOException {
        out.defaultWriteObject();
        out.writeObject(players);
    }
    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        players = (ArrayList<Player>)ois.readObject();
    }
}
