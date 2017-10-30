package Model;

import Model.Account;
import Model.Tournament;

import java.util.*;

public class Administrator extends Account{

    private ArrayList<Tournament> tournaments = new ArrayList<Tournament>();

    public Administrator(String name) {
        super(name);
    }

    /**Metodo para que el controller pueda tener acceso a los torneos y asi poder mostrarlos al admin*/
    public ArrayList<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(ArrayList<Tournament> tournaments) {this.tournaments=tournaments;}

    /**Este metodo deberia ser el primero en llamarse cuando desde el front el admin
     * actualiza los jugadores de un equipo en un torneo. Llama metodo refresh en cascada desde la clase torneo
     * Aun me queda la duda de si deberiamos llamar estos metodos desde las clases del model o de otras.*/
    public void refreshTeam(String tournamentName, Properties p, Team t, String name) {
        for(Tournament tournament : tournaments) {
            if(tournament.getName().compareTo(tournamentName) == 0) {
                tournament.refreshTeamName(p, t, name);
            }
        }
    }

    /**Para cuando el administrador quiera crear un nuevo torneo. Mi idea es que desde el controller se instancie la
     * clase torneo para poder ingresarla directamente*/
    public void addTournament(Tournament t){
        tournaments.add(t);
    }

    public boolean hasTournament(String tournamentName) {
        for (Tournament t : tournaments) {
            if(t.getName().compareTo(tournamentName) == 0)
                return true;
        }
        return false;
    }

    public Tournament getTournament(String name) {
        for(Tournament t : tournaments) {
            if(t.getName().compareTo(name) == 0)
                return t;
        }
        return null;
    }
}
