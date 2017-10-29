package Model;

import java.util.HashMap;
import java.util.Vector;

public class Team extends Identifiable {

    private int maxPlayers;// maximma cantidad de jugadores incluyendo suplentes
    private HashMap<Integer,Player> players;

    public Team(String name, int maxPlayers) {
        super(name);
        if (maxPlayers < 1){
            throw new IllegalArgumentException("Cantidad de players debe ser mayor a 1");
        }
        this.maxPlayers = maxPlayers;
        this.players = new HashMap<Integer,Player>();
    }

    public void add(Player j, int position) throws CompleteTeamException, OccupiedPositionExcepetion, PlayerExistsException{
        if (position < 1 || position > maxPlayers)
            throw new IllegalArgumentException("Posicion ebe ser entre 1 y el maximo de players permitidos.");
        if (players.size() >= maxPlayers)
            throw new CompleteTeamException();
        if (players.containsKey(position))
            throw new OccupiedPositionExcepetion();
        if (players.containsValue(j))
            throw new PlayerExistsException();
        players.put(position,j);
    }

    public void remove(int position){
        players.remove(position);
    }

    public void refreshPlayer(Properties p, int pos) {
        players.get(pos).refresh(p);
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

    public Player[] getPlayers() {
    return (Player[]) players.values().toArray();
    }

    private class PlayerExistsException extends Exception {
        public PlayerExistsException() {
            super("El jugador ya se encuentra en el equipo");
        }
    }
    private class OccupiedPositionExcepetion extends Exception {
        public OccupiedPositionExcepetion() {
            super("La position ya se ecuentra ocupada en este equipo");
        }
    }
    private class CompleteTeamException extends Exception {
        public CompleteTeamException() {
            super("El equipo esta completo");
        }
    }
}
