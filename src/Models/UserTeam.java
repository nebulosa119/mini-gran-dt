package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class UserTeam implements Serializable{

    private static final long serialVersionUID = 1L;

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
        System.out.println("Points: " + points);
    }

    public int getUserPoints() {
        return points;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(points);
        out.writeObject(players);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        points = ois.readInt();
        players= (ArrayList<Player>) ois.readObject();
    }
}
