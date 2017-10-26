package src.Model;

import java.util.HashMap;

public class User extends Account {
    private HashMap<Integer, Team> teams;

    public User(String name, int dni, String passwd) {
        super(name, dni, passwd);
        this.teams = new HashMap<>();
    }

    // si no se contiene al torneo, se lo agrega, caso contrario se pisa el equipo
    public void addTeam(int idTournament, Team e){
        if (idTournament < 0)
            throw new IllegalArgumentException("Id del torneo debe ser >= 0");
        teams.put(idTournament, e);
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
