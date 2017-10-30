package Model;

import java.util.ArrayList;

public class Team extends Identifiable {

    private int maxPlayers;// maximma cantidad de jugadores incluyendo suplentes
    private ArrayList<Player> players;

    public Team(String name, int maxPlayers) {
        super(name);
        if (maxPlayers < 1){
            throw new IllegalArgumentException("Cantidad de players debe ser mayor a 1");
        }
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void add(Player p) throws CompleteTeamException, PlayerExistsException{
        if (players.size() >= maxPlayers)
            throw new CompleteTeamException();
        if (players.contains(p))
            throw new PlayerExistsException();
        players.add(p);
    }

    public void refreshPlayer(Properties p, String name) {
        for (Player player : players) {
            if (player.name.equals(name))
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
