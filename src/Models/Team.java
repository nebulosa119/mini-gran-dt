package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Team extends Identifiable implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Player> players;
    private int maxPlayers;

    public Team(String name, int maxPlayers) {
        super(name);
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
    }

    public Team(Team team, int maxPlayers) {
        this(team.getName(),maxPlayers);
        players.addAll(team.getPlayers());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getNewPlayers(){
        ArrayList<Player> resp = new ArrayList<>();
        for (Player player :players) {
            resp.add(new Player(player.getName()));
        }
        return resp;
    }

    public int getMaxPlayers() { return maxPlayers; }

    public void add(Player p) throws CompleteTeamException{
        if (players.size() < maxPlayers && !players.contains(p))
            players.add(p);
        else
            throw new CompleteTeamException();
    }

    public void refresh(Map<String,Properties> dataPlayers) {
        for (Player myPlayer : players) {
            myPlayer.refresh(dataPlayers.get(myPlayer.getName()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Team))
            return false;
        Team team = (Team) o;
        return super.equals(team);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 2; // para diferenciarlos
        return result;
    }

    @Override
    public String toString() {
        return name+"{"+Arrays.toString(players.toArray())+'}';
    }

    public class CompleteTeamException extends Exception {
        public CompleteTeamException() {
            super("El equipo esta completo");
        }
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

