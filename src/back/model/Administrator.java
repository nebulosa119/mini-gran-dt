package back.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

    /**
     * Modela al Administrador de cada club de Futbol.
     * Este se ocupa de subir los datos del torneo
    * */
public class Administrator extends User {

    private static final long serialVersionUID = 1L;

    private Map<PhysicalTournament, ArrayList<DT>> tournamentUsers = new HashMap<>();

    public Administrator(String name) {
        super(name);
    }

    /**Metodo para que el front.controller pueda tener acceso a los torneos y asi poder mostrarlos al admin*/
    public Set<PhysicalTournament> getTournaments() {
        return tournamentUsers.keySet();
    }
        /**
         * @param name el nombre del torneo a retornar
         * @return el trorneo especificado*/
    public PhysicalTournament getTournament(String name) {
        for (PhysicalTournament t : tournamentUsers.keySet()) {
            if (t.getName().compareTo(name) == 0)
                return new PhysicalTournament(t);
        }
        return null;
    }
    /** Crea un torneo
     * @param tournamentName nombre del torneo a crear
     * @param maxPlayers cantidad maxima de juagores en el torneo
     * @return true si no existe ya un torneo con ese nombre, false si existe*/
    public boolean createTournament(String tournamentName, int maxPlayers){
        if(tournamentName.equals("") || maxPlayers<0)
            return false;

        PhysicalTournament physicalTournament = new PhysicalTournament(tournamentName, maxPlayers);
        physicalTournament.setAdministrator(this);
        addTournament(physicalTournament);
        return true;
    }

    public ArrayList<DT> getOrderedDTs(PhysicalTournament physicalTournament) {
        ArrayList<DT> users = tournamentUsers.get(physicalTournament);
        users.sort(new Comparator<DT>() {
            @Override
            public int compare(DT t, DT t1) {
                return t1.getPoints(physicalTournament) - t.getPoints(physicalTournament);
            }
        });
        return users;
    }
    /**Para cuando el administrador quiera crear un nuevo torneo.
     * @param t el torneo a agregar*/
    public void addTournament(PhysicalTournament t){
        tournamentUsers.put(new PhysicalTournament(t),new ArrayList<>());
    }
    /**
     * Chequea si el torneo existe
     * @param physicalTournament el torneo a buscar
     * @return true si existe, false caso contrario*/
    public boolean containsTournament(PhysicalTournament physicalTournament) {
        return tournamentUsers.containsKey(physicalTournament);
    }
    /**
     * Agrega un Dt a un torneo
     * @param tournament nombre del toreno a ser agregado*/
    public void addDT(String tournament, DT DT) {
        PhysicalTournament tour = getTournament(tournament);
        tournamentUsers.get(tour).add(DT);
    }
    /**
     * Se ocupa de pasar la informacion recivida el View a las demas clases
     * conteniadas en el administrador. Se refreshea las inforamcion de cada juagador de los torneo
     * Cada juagador se eecuntra en un equipo, estos estan en torneos.
     * @param dataTournaments La informacion recibida del front a ser transmitida hacia las demas clases*/
    public void refresh(Map<String, Map<String, Map<String, PhysicalPlayer.Properties>>> dataTournaments) {
        for (PhysicalTournament myTour : tournamentUsers.keySet()) {
            if(dataTournaments.get(myTour.getName()) != null) {
                myTour.refresh(dataTournaments.get(myTour.getName()));
                refreshDTs(myTour.getName(), dataTournaments.get(myTour.getName()));
            }
        }
    }
    /**
     * Se encarga de pasar la informacion a los DTS para que hagan el update en sus rankings i plata disponible
     * @param tourName nombre del torneo que se está refreshiando
     * @param tournament informacion nueva del torneo
     * */
    private void refreshDTs(String tourName, Map<String,Map<String,PhysicalPlayer.Properties>> tournament) {
        PhysicalTournament tour = getTournament(tourName);
        ArrayList<DT> DTS = tournamentUsers.get(tour);
        if (DTS != null) {
            for (DT dt : DTS) {
                dt.refreshPoints(tour, unifyPlayers(tournament));
            }
        }
    }
    /**Junta todos los jugadores de todos los teams en un solo arreglo clavevalor
     * @param teams los euipos a unificar
     * @return un mapa con todos los jugaroes de los equipos*/
    private Map<String,PhysicalPlayer.Properties> unifyPlayers(Map<String, Map<String, PhysicalPlayer.Properties>> teams)  {
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
