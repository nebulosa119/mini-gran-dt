package Model;

import Model.Account;
import Model.Tournament;

import java.util.*;

public class Administrator extends Account{

    private ArrayList<Tournament> tournaments = new ArrayList<Tournament>();

    public Administrator(String name) {
        super(name);
    }

    /**Este metodo deberia ser el primero en llamarse cuando desde el front el admin
     * actualiza los jugadores de un equipo en un torneo. Llama metodo refresh en cascada desde la clase torneo
     * Aun me queda la duda de si deberiamos llamar estos metodos desde las clases del model o de otras.*/
    public void refreshTeam(String tournamentName, Properties p, Team t, int pos) {
        for(Tournament tournament : tournaments) {
            if(tournament.getName().compareTo(tournamentName) == 0) {
                tournament.refreshTeamPosition(p, t, pos);
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


    public ArrayList<Tournament> getTournaments(){
       return tournaments;
    }



}
