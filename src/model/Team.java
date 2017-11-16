package model;

import model.exceptions.CompleteTeamException;
import model.exceptions.ExistentNameException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Modela un equipo de futbol genérico, puede ser fisico o virtual
 */
public abstract class Team implements Serializable{

    int max_players;
    ArrayList<PhysicalPlayer> physicalPlayers = new ArrayList<>();

    Team(int max_players) {
        this.max_players = max_players;
    }
    /**
     * Agrega un jugador al equipo
     * @param p  el nuevo jugador*/
    void addPlayer(PhysicalPlayer p) throws CompleteTeamException, ExistentNameException {
        if(physicalPlayers.size() == max_players)
            throw new CompleteTeamException();
        if (physicalPlayers.contains(p))
            throw new ExistentNameException();
        physicalPlayers.add(p);
    }
    /**
     * REmueve un jugador del equipo
     * @param p  el jugador a ser removido*/
    void removePlayer(PhysicalPlayer p) {
        physicalPlayers.remove(p);
    }
    /**
     * @return un array con los jugadores*/
    public ArrayList<PhysicalPlayer> getPhysicalPlayers() {
        return physicalPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Team))
            return false;
        Team aux = (Team) o;
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
