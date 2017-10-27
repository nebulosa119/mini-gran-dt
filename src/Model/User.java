package Model;

import java.util.HashMap;

public class User extends Account {
    private HashMap<String, Team> teams; // String para reconocer el tournament por el nombre

    public User(String name) {
        super(name);
        this.teams = new HashMap<>();
    }

    // si no se contiene al torneo, se lo agrega, caso contrario se pisa el equipo
    public void addTeam(String tournamentName, Team e){
        if (tournamentName == null)
            throw new NullPointerException("Id del torneo debe ser >= 0");
        teams.put(tournamentName, e);
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
