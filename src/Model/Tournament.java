package Model;

import java.util.Vector;

public class Tournament extends Identifiable {

    private Vector<Team> teams;
    // para llevar registro de los jugadores
    //private HashMap<Date,> por simplificaicon no vamos a llevar(por ahora) registro de las fechas, eso
    // se opcupara el adiministrador en su pagina web.....

    public Tournament(String name) {
        super(name);
        this.teams = new Vector<>();
    }

    public void refreshTeamPosition(Properties p, Team t, int pos) {
        for(Team team: teams) {
            if(team.equals(t)) {
                team.refreshPlayer(p, pos);
            }
        }
    }

    /**Para cuando se cree el torneo y los equipos*/
    public void addTeam(Team t) {
        teams.add(t);
    }

    public boolean hasTeam(Team t) {
        for(Team team : teams) {
            if(t.equals(team))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Tournament))
            return false;
        Tournament tournament = (Tournament) o;
        return super.equals(tournament);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 7; // para diferenciarlo d
        return result;
    }

}
