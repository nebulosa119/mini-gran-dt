package Models;

import java.util.ArrayList;

public class TournamentsAdministration {

    private ArrayList<Tournament> tournaments = new ArrayList<>();
    private ArrayList<User> usersParticipating = new ArrayList<>();
    private Administrator admin;

    public TournamentsAdministration(Administrator admin) {
        this.admin = admin;
    }

    public boolean isAdministrating(Tournament t) {
        return tournaments.contains(t);
    }

    public void administerNewTournament(Tournament t) {
        tournaments.add(t);
    }

    public void closeTournament(Tournament t) {
        tournaments.remove(t);
    }

    public Tournament getTournament(Tournament t) {
        return tournaments.get(tournaments.indexOf(t));
    }

    public ArrayList<Tournament> getTournaments() {
        return tournaments;
    }

    public void addUser(User u) {
        usersParticipating.add(u);
    }

    public void refreshUsers(Player.Properties p, Tournament t) {
        for(User u : usersParticipating) {
            u.getUserTeams().refresh(p, t);
        }
    }
}
