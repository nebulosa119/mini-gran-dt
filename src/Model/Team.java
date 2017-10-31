package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Team extends Identifiable {

    private ArrayList<Player> players;

    public Team(String name) {
        super(name);
        this.players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void add(Player p, int maxPlayers) throws CompleteTeamException, PlayerExistsException{
        if (players.size() >= maxPlayers)
            throw new CompleteTeamException();
        if (players.contains(p))
            throw new PlayerExistsException();
        players.add(p);
    }

    public void refreshPlayers(HashMap<String, Properties> prop) {
        for (Player player : players) {
            Properties p = prop.get(player.getName());
            player.refresh(p);
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

    public class PlayerExistsException extends Exception {
        public PlayerExistsException() {
            super("El jugador ya se encuentra en el equipo");
        }
    }

    public class CompleteTeamException extends Exception {
        public CompleteTeamException() {
            super("El equipo esta completo");
        }
    }
}
