package Model;

import java.util.Map;
import java.util.TreeMap;

public class User extends Account {

    protected int puntaje;
    private Map<String, Team> teams; // String para reconocer el tournament por el nombre

    public User(String name) {
        super(name);
        this.teams = new TreeMap<String, Team>();
        puntaje = 0;
    }

    // si no se contiene al torneo, se lo agrega, caso contrario se pisa el equipo
    public void addTeam(String tournamentName, Team e){
        if (tournamentName == null)
            throw new NullPointerException("Id del torneo debe ser >= 0");
        teams.put(tournamentName, e);
    }

    /**Este metodo no deberia ir a administrator?*/
    public Tournament getTournament(String name) {
        for(Tournament t : tournaments) {
            if(t.getName().compareTo(name) == 0)
                return t;
        }
        return null;
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

}
