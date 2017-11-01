package Model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class User extends Account {

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

/*    @Override
    public ArrayList<String> getTournamentNames() {
        ArrayList<String> tourNames = new ArrayList<>();
        tourNames.addAll(teams.keySet());
        return tourNames;
    }

    @Override
    public void refresh(ArrayList<Tournament> tournaments) {

    }*/

    public Team getTeam(String tourName){
        if (teams.containsKey(tourName))
            return new Team(teams.get(tourName));
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

    public void sell(Player p, Tournament t) {
        if(teams.get(t.getName()).getPlayers().contains(p)) {
            teams.get(t.getName()).getPlayers().remove(p); /**Queda medio feo*/
        }
    }

    /**Falta definir MAX_CAPACITY*/
    public boolean canBuy(Player p) {
        return money >= p.getPrice();
    }

    public boolean hasCapacity(Tournament t) {
        if(!teams.containsKey(t)) return true; /**Por programación defensiva*/
        if(teams.get(t).getPlayers().size() < Team.MAX_CAPACITY) return true;
        return false;
    }

}