package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Administrator extends User {

    private static final long serialVersionUID = 1L;

    private Map<PhysicalTournament, ArrayList<DT>> tournamentUsers = new HashMap<>();

    public Administrator(String name) {
        super(name);
    }

    /**Metodo para que el Controllers pueda tener acceso a los torneos y asi poder mostrarlos al admin*/
    public Set<PhysicalTournament> getTournaments() {
        return tournamentUsers.keySet();
    }

    public PhysicalTournament getTournament(String name) {
        for (PhysicalTournament t : tournamentUsers.keySet()) {
            if (t.getName().compareTo(name) == 0)
                return new PhysicalTournament(t);
        }
        return null;
    }

    public boolean createTournament(String tournamentName, int maxPlayers){
        if(tournamentName.equals("") || maxPlayers<0)
            return false;

        PhysicalTournament physicalTournament = new PhysicalTournament(tournamentName, maxPlayers);
        physicalTournament.setAdministrator(this);
        addTournament(physicalTournament);
        return true;
    }

    public ArrayList<DT> getOrderedUsers(PhysicalTournament physicalTournament) {
        ArrayList<DT> users = tournamentUsers.get(physicalTournament);
        users.sort(new Comparator<DT>() {
            @Override
            public int compare(DT t, DT t1) {
                return t1.getPoints(physicalTournament) - t.getPoints(physicalTournament);
            }
        });
        return users;
    }
    /**Para cuando el administrador quiera crear un nuevo torneo. Mi idea es que desde el Controllers se instancie la
       * clase torneo para poder ingresarla directamente*/
    public void addTournament(PhysicalTournament t){
        tournamentUsers.put(new PhysicalTournament(t),new ArrayList<>());
    }

    public boolean containsTournament(PhysicalTournament physicalTournament) {
        return tournamentUsers.containsKey(physicalTournament);
    }
 
    public void addUser(String tournament, DT DTAccount) {
        PhysicalTournament tour = getTournament(tournament);
        tournamentUsers.get(tour).add(DTAccount);
    }

    public void refresh(Map<String, Map<String, Map<String, PhysicalPlayer.Properties>>> dataTournaments) {
        for (PhysicalTournament myTour : tournamentUsers.keySet()) {
            myTour.refresh(dataTournaments.get(myTour.getName()));
            refreshUsers(myTour.getName(), dataTournaments.get(myTour.getName()));
        }
    }

    private void refreshUsers(String tourName, Map<String,Map<String, PhysicalPlayer.Properties>> tournament) {
        PhysicalTournament tour = getTournament(tourName);
        ArrayList<DT> DTAccounts = tournamentUsers.get(tour);
        if (DTAccounts != null) {
            for (DT DTAccount : DTAccounts) {
                DTAccount.refreshPoints(tour, unifyPlayers(tournament));
            }
        }
    }
    //junta todos los jugadores de todos los teams en un solo arreglo clavevalor
    private Map<String, PhysicalPlayer.Properties> unifyPlayers(Map<String, Map<String, PhysicalPlayer.Properties>> teams)  {
        Map<String, PhysicalPlayer.Properties> unified = new HashMap<>();
        for (Map<String, PhysicalPlayer.Properties> team : teams.values()) {
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
        tournamentUsers = (Map<PhysicalTournament, ArrayList<DT>>) ois.readObject();
    }
}
