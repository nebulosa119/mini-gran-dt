package Models;

import Models.Exceptions.CompleteTeamException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PhysicalTeam extends Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public PhysicalTeam(String name, int maxPlayers) {
        super(maxPlayers);
        this.name = name;
    }

    PhysicalTeam(String name, PhysicalTeam team, int maxPlayers) {
        this(team.getName(),maxPlayers);
        this.name = name;
        physicalPlayers.addAll(team.getPhysicalPlayers());
    }

    public ArrayList<PhysicalPlayer> getPhysicalPlayers() {
        return physicalPlayers;
    }

    public String getName() {
        return name;
    }

    public void add(PhysicalPlayer p) throws CompleteTeamException{
        if (physicalPlayers.size() < max_players && !physicalPlayers.contains(p))
            physicalPlayers.add(p);
        else
            throw new CompleteTeamException();
    }

    void refresh(Map<String, PhysicalPlayer.Properties> dataPlayers) {
        for (PhysicalPlayer myPhysicalPlayer : physicalPlayers) {
            myPhysicalPlayer.refresh(dataPlayers.get(myPhysicalPlayer.getName()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhysicalTeam team = (PhysicalTeam) o;
        return super.equals(o) && (name != null ? name.equals(team.name) : team.name == null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode()*3 : 0;
    }

    @Override
    public String toString() {
        return name+"{"+Arrays.toString(physicalPlayers.toArray())+'}';
    }

    private void writeObject(ObjectOutputStream out)throws IOException{
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeObject(physicalPlayers);
    }
    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        physicalPlayers = (ArrayList<PhysicalPlayer>)ois.readObject();
    }
}

