package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Team extends Identifiable {

    private ArrayList<Player> players;

    public Team(String name) {
        super(name);
        this.players = new ArrayList<>();
    }

    public Team(Team team) {
        this(team.getName());
        players.addAll(team.getPlayers());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getNewPlayers(){
        ArrayList<Player> resp = new ArrayList<>();
        for (Player player :players) {
            System.out.println(player.getName()+" "+player.getProperties());
            resp.add(new Player(player.getName()));
        }
        return resp;
    }

    public void add(Player p, int maxPlayers){
        if (players.size() < maxPlayers && !players.contains(p))
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

    public void refresh(Team team) {
        for (Player myPlayer:players) {
            for (Player dataPlayer:team.getPlayers()) {
                if (myPlayer.getName().equals(dataPlayer.getName())){
                    myPlayer.refresh(dataPlayer);
                    break;
                }
            }
        }
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
