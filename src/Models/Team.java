package Models;

import Models.Exceptions.CompleteTeamException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Player> players;
    private String name;
    private int maxPlayers; // esto no tiene que estar aca, es de tournament, hay que arreglarlo

    public Team(String name, int maxPlayers) {
        this.name = name;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
    }

    public Team(Team team, int maxPlayers) {
        this(team.getName(),maxPlayers);
        players.addAll(team.getPlayers());
    }

    public Team() {
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() { return maxPlayers; }

    public void add(Player p) throws CompleteTeamException{
        if (players.size() < maxPlayers && !players.contains(p))
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

        Team team = (Team) o;

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
        players = (ArrayList)ois.readObject();
    }
}

