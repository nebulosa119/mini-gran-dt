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

    public void refresh(Set<Tournament> tournaments) {
        for (Tournament myTour : tournamentUsers.keySet()) {
            for (Tournament dataTour : tournaments) {
                if (myTour.getName().equals(dataTour.getName())) {
                    myTour.refresh(dataTour);
                    refreshUsers(dataTour);
                    break;
                }
            }
        }
    }

    private void refreshUsers(Tournament tournament) {
        for (User user : tournamentUsers.get(tournament)) {
            user.refreshPoints(tournament.getName());
        }
    }


    /**Metodo para que el Controller pueda tener acceso a los torneos y asi poder mostrarlos al admin*/
    public Set<Tournament> getTournaments() {
        return tournamentUsers.keySet();
    }

    /**Este metodo deberia ser el primero en llamarse cuando desde el front el admin
     * actualiza los jugadores de un equipo en un torneo. Llama metodo refresh en cascada desde la clase torneo
     * Aun me queda la duda de si deberiamos llamar estos metodos desde las clases del Model o de otras.*/
   /* public void refreshTeam(String tournamentName, Properties p, Team t, String name) {
        for(Tournament tournament : tournaments) {
            if(tournament.getName().compareTo(tournamentName) == 0) {
                tournament.refreshTeamName(p, t, name);
            }
        }
    }*/

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

    @Override
    public String toString() {
        return "Administrator{" +
                "name='" + name + Arrays.toString(tournamentUsers.keySet().toArray()) + '}';
    }
}
