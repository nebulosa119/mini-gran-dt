package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Administrator extends Account {

    private static final long serialVersionUID = 1L;

    private Map<Tournament,ArrayList<UserDT>> tournamentUsers = new HashMap<>();

    public Administrator(String name) {
        super(name);
    }

    /**Metodo para que el Controllers pueda tener acceso a los torneos y asi poder mostrarlos al admin*/
    public Set<Tournament> getTournaments() {
        return tournamentUsers.keySet();
    }

    public Tournament getTournament(String name) {
        for (Tournament t : tournamentUsers.keySet()) {
            if (t.getName().compareTo(name) == 0)
                return new Tournament(t);
        }
        return null;
    }

    public boolean createTournament(String tournamentName, int maxPlayers){
        if(tournamentName.equals("") || maxPlayers<0)
            return false;

        Tournament tournament = new Tournament(tournamentName, maxPlayers);
        tournament.setAdministrator(this);
        addTournament(tournament);
        return true;
    }

    public ArrayList<UserDT> getOrderedUsers(Tournament tournament) {
        ArrayList<UserDT> users = tournamentUsers.get(tournament);
        users.sort(new Comparator<UserDT>() {
            @Override
            public int compare(UserDT t, UserDT t1) {
                return t.getPoints(tournament) - t1.getPoints(tournament);
            }
        });
        return users;
    }
    /**Para cuando el administrador quiera crear un nuevo torneo. Mi idea es que desde el Controllers se instancie la
       * clase torneo para poder ingresarla directamente*/
    public void addTournament(Tournament t){
        tournamentUsers.put(new Tournament(t),new ArrayList<>());
    }

    public boolean containsTournament(Tournament tournament) {
        return tournamentUsers.containsKey(tournament);
    }
 
    public void addUser(String tournament, UserDT userDT) {
        Tournament tour = getTournament(tournament);
        tournamentUsers.get(tour).add(userDT);
    }

    public void refresh(Map<String, Map<String, Map<String, Player.Properties>>> dataTournaments) {
        for (Tournament myTour : tournamentUsers.keySet()) {
            myTour.refresh(dataTournaments.get(myTour.getName()));
            refreshUsers(myTour.getName(), dataTournaments.get(myTour.getName()));
        }
    }

    private void refreshUsers(String tourName, Map<String,Map<String, Player.Properties>> tournament) {
        Tournament tour = getTournament(tourName);
        ArrayList<UserDT> userDTS = tournamentUsers.get(tour);
        if (userDTS != null) {
            for (UserDT userDT : userDTS) {
                //userDT.refreshPoints(tourName,unifyPlayers(tournament));}
                userDT.refreshPoints(tour, unifyPlayers(tournament));
            }
        }
    }
    //junta todos los jugadores de todos los teams en un solo arreglo clavevalor
    private Map<String, Player.Properties> unifyPlayers(Map<String, Map<String, Player.Properties>> teams)  {
        Map<String, Player.Properties> unified = new HashMap<>();
        for (Map<String, Player.Properties> team : teams.values()) {
            unified.putAll(team);
        }
        return unified;
    }
 
    @Override
     public String toString() {
        return "Administrator{" + "name='" + name + Arrays.toString(tournamentUsers.keySet().toArray()) + '}';
    }


    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeObject(tournamentUsers);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        name = ois.readUTF();
        tournamentUsers = (Map<Tournament, ArrayList<UserDT>>) ois.readObject();
    }
}
