package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;


// implementa comparable con respecto a los puntos para ordenarlos en cada tournament y cuando se muestra la lista de puntajes
// solo hay que recorrer la lista e imprimirlos
public class User extends Account{

    private static final long serialVersionUID = 1L;

    private int points; /**Pa qué protected si no lo necesitamos acceder desde subclases de User?*/
    private int money;
    private final static int INITIAL_AMOUNT = 1000; /**Discutir el monto después*/
    private Map<String, Team> teams; // String para reconocer el tournament por el nombre

    public User(String name) {
        super(name);
        this.teams = new TreeMap<String, Team>();
        points = 0;
        money = INITIAL_AMOUNT;
    }

    public Team getTeam(String tourName){
        if (teams.containsKey(tourName))
            return teams.get(tourName);
        return null;
    }
    // si no se contiene al torneo, se lo agrega, caso contrario se pisa el equipo
    public void addTeam(String tournamentName, Team e){
        if (tournamentName == null)
            throw new NullPointerException("Id del torneo debe ser >= 0");
        teams.put(tournamentName, e);
    }

    public Map<String, Team> getTeams() {
        return teams;
    }

    public void refreshPoints(String tournament, Map<String, Player.Properties> players) {
        Team team = teams.get(tournament);
        for (Player p : team.getPlayers()) {
            points += players.get(p.getName()).getPoints();
        }
    }

    public int getPoints() {
        return points;
    }

    public void sell(Player p, Tournament t) {
        if(teams.get(t.getName()).getPlayers().contains(p)) {
            teams.get(t.getName()).getPlayers().remove(p); /**Queda medio feo*/
            money += p.getPrice();
        }
    }

    /**Falta definir MAX_CAPACITY*/
    public boolean canBuy(Player p) {
        return money >= p.getPrice();
    }

    public boolean hasCapacity(Tournament t) {
        if(!teams.containsKey(t)) return true; /**Por programación defensiva*/
        Team team = teams.get(t);
        if(team.getPlayers().size() < team.getMaxPlayers()) return true;
        return false;
    }

    public void buy(String name, Player p) {
        teams.get(name).getPlayers().add(p);
        money -= p.getPrice();
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return super.equals(user);
    }

    @Override
    public int hashCode(){
        int result = super.hashCode();
        result = result * 5;
        return result;
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeInt(points);
        out.writeInt(money);
        out.writeObject(teams);

    }
    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        points = ois.readInt();
        money = ois.readInt();
        teams = (Map<String, Team>) ois.readObject();
    }
}