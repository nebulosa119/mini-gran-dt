package Models;

import Models.Exceptions.CompleteTeamException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PhysicalTeam extends AbstractTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public PhysicalTeam(String name, int maxPlayers) {
        super(maxPlayers);
        this.name = name;
    }

    public PhysicalTeam(String name, PhysicalTeam team, int maxPlayers) {
        this(team.getName(),maxPlayers);
        this.name = name;
        players.addAll(team.getPlayers());
    }

    /**public PhysicalTeam() {
        players = new ArrayList<>();
    */

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public void add(Player p) throws CompleteTeamException{
        System.out.println(max_players);
        if (players.size() < max_players && !players.contains(p))
            players.add(p);
        else
            throw new CompleteTeamException();
    }

    void refresh(Map<String, Player.Properties> dataPlayers) {
        System.out.println(dataPlayers);
        for (Player myPlayer : players) {
            System.out.println(myPlayer.getName());
            myPlayer.refresh(dataPlayers.get(myPlayer.getName()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhysicalTeam team = (PhysicalTeam) o;
        if(!super.equals(o)) return false;
        return name != null ? name.equals(team.name) : team.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode()*3 : 0;
    }

    @Override
    public String toString() {
        return name+"{"+Arrays.toString(players.toArray())+'}';
    }

    private void writeObject(ObjectOutputStream out)throws IOException{
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeObject(players);
    }
    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        players = (ArrayList<Player>)ois.readObject();
    }
}

