package Model;

import java.util.*;

public class Administrator extends Account{

    private Map<Tournament,TreeSet<User>> tournamentUsers = new HashMap<>();

    public Administrator() {
        super();
    }

    public Administrator(String name) {
        super(name);
    }

    public Set<String> getTournamentNames() {
        Set<String> resp = new HashSet<>();
        for (Tournament t : tournamentUsers.keySet()) {
            resp.add(t.getName());
        }
        return resp;
    }

    public void refresh(Map<String,Map<String,Map<String,Properties>>> dataTournaments) {
        for (Tournament myTour : tournamentUsers.keySet()) {
            myTour.refresh(dataTournaments.get(myTour.getName()));
            refreshUsers(myTour.getName(), dataTournaments.get(myTour.getName()));
        }
    }

    private void refreshUsers(String tourName, Map<String,Map<String,Properties>> tournament) {
        TreeSet<User> users = tournamentUsers.get(tourName);
        if (users!=null) {
            for (User user : users) {
                user.refreshPoints(tourName,unifyPlayers(tournament));
            }
        }
    }

    //junta todos los jugadores de todos los teams en un solo arreglo clave-valor
    private Map<String,Properties> unifyPlayers(Map<String,Map<String,Properties>> teams) {
        Map<String,Properties> unified = new HashMap<>();
        for (Map<String,Properties> team : teams.values()) {
            unified.putAll(team);
        }
        return unified;
    }

    /**Metodo para que el Controller pueda tener acceso a los torneos y asi poder mostrarlos al admin*/
    public Set<Tournament> getTournaments() {
        return tournamentUsers.keySet();
    }

    /**Para cuando el administrador quiera crear un nuevo torneo. Mi idea es que desde el Controller se instancie la
     * clase torneo para poder ingresarla directamente*/
    public void addTournament(Tournament t){
        tournamentUsers.put(new Tournament(t),null);
    }

    public boolean hasTournament(String tournamentName) {
        for (Tournament t : tournamentUsers.keySet()) {
            if (t.getName().equals(tournamentName))
                return true;
        }
        return false;
    }

    public Tournament getTournament(String name) {
        for (Tournament t : tournamentUsers.keySet()) {
            if (t.getName().compareTo(name) == 0)
                return new Tournament(t);
        }
        return null;
    }

    public void addUser(String tournament, User user) {
        tournamentUsers.get(tournament).add(user);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "name='" + name + Arrays.toString(tournamentUsers.keySet().toArray()) + '}';
    }
}
