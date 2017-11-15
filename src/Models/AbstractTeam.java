package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractTeam implements Serializable{

    int max_players;
    ArrayList<PhysicalPlayer> physicalPlayers = new ArrayList<>();

    AbstractTeam(int max_players) {
        this.max_players = max_players;
    }

    void addPlayer(PhysicalPlayer p) {
        if (!physicalPlayers.contains(p))
            physicalPlayers.add(p);
    }

    void removePlayer(PhysicalPlayer p) {
        physicalPlayers.remove(p);
    }

    public ArrayList<PhysicalPlayer> getPhysicalPlayers() {
        return physicalPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof AbstractTeam))
            return false;
        AbstractTeam aux = (AbstractTeam) o;
        if(max_players != aux.max_players)
            return false;
        for(PhysicalPlayer p1 : physicalPlayers) {
            for(PhysicalPlayer p2: aux.getPhysicalPlayers())
                if(!p1.equals(p2))
                    return false;
        }
        return true;
    }

    private void writeObject(ObjectOutputStream out)throws IOException {
        out.defaultWriteObject();
        out.writeObject(physicalPlayers);
    }
    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        physicalPlayers = (ArrayList<PhysicalPlayer>)ois.readObject();
    }
}
