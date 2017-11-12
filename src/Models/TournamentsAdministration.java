package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TournamentsAdministration implements Serializable{

    private static final long serialVersionUID = 1L;

    private ArrayList<Tournament> tournaments = new ArrayList<>();
    private ArrayList<UserDT> usersParticipating = new ArrayList<>();
    private Map<Tournament, ArrayList<UserDT>> usersParticipatingMap = new HashMap<>();
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

    public void addUser(Tournament t, UserDT u) {
        usersParticipating.add(u);
    }

    public void refreshUsers(Player.Properties p, Tournament t) {
        for(UserDT u : usersParticipating) {
            u.getUserTeams().refresh(p, t);
        }
    }
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(tournaments);
        out.writeObject(usersParticipating);
        out.writeObject(admin);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        tournaments = (ArrayList<Tournament>) ois.readObject();
        usersParticipating = (ArrayList<UserDT>) ois.readObject();
        admin = (Administrator) ois.readObject();
    }
}
