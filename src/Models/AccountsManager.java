package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Clase que maneja todas las cuentas de usuarios del programa Mini Gran DT.
 *
 * @author
 */
public class AccountsManager implements Serializable{

    private static final long serialVersionUID = 1L;

    private static ArrayList<Account> accounts;
    private static Account account;
    private static AccountsManager instance;

    private AccountsManager(){
        instance = this;
    }

    /** Carga los usuarios existentes al programa */
    public static void loadAccounts() {
        //accounts = (ArrayList<Account>) FileManager.unserializeObject(Types.USER.fileName);// no se como se soluciona
        accounts = new ArrayList<>();
        simulateAccounts();
    }

    public ArrayList<Administrator> getAdmins() {
        ArrayList<Administrator> aux = new ArrayList<>();
        for(Account a : accounts) {
            if(a instanceof Administrator)
                aux.add((Administrator)a);
        }
        return aux;
    }

    public static AccountsManager getInstance(){
        if(instance == null)
            instance = new AccountsManager();
        return instance;
    }

    public static boolean contains(String username) {
        if(username.equals(""))
            return false;
        return getAccount(username) != null;
    }

    public static boolean createAccount(String username) {
        if(username.equals(""))
            return false;
        accounts.add(new User(username));
        return true;
    }

    public static void createAccount(Account account) {
        accounts.add(account);
    }

    public static Account getAccount(String username) {
        for (Account aux: accounts) {
            if (aux.getName().equals(username))
                return aux;
        }
        return null;
    }

    public static void setAccount(String accountName) {
        account = getAccount(accountName);
    }

    private static void simulateAccounts(){
        String[] teamNames = new String[]{"Sonido Caracol", "Lincoln", "Matambre Reloaded", "C.A. Hay Combate", "Tu Marido", "Cerezas Inocentes", "Piraña", "La Vieja Señora", "Asfalten Kayen", "Ultimo Momento", "El Equipo de Carama", "FC Ronvodwhisky", "Tenedor Libre", "Herederos del Ñoqui", "Savio F.C.", "Pato Criollo", "Extra Brutt", "El Mago y su Jauria", "Colectivo San Juan", "El Nono Michelin", "Submarino Amarilo", "Jamaica Bajo Cero", "Los Borbotones", "No Manzana", "Corta el Pasto", "Furia FC", "La Vino Tinto", "Lineo B", "Te lo Juro por las Nenas", "La Nave Fulbo Clu", "Argentinos Juniors	Buenos Aires", "Belgrano	Córdoba", "Boca Juniors	Buenos Aires", "Chacarita Juniors	Villa Maipú", "Colón	Santa Fe", "Estudiantes	La Plata", "Ferro Carril Oeste	Buenos Aires", "Gimnasia y Esgrima	Jujuy", "Gimnasia y Esgrima	La Plata", "Independiente	Avellaneda", "Instituto	Córdoba", "Lanús	Lanús", "Newell's Old Boys	Rosario", "Racing Club	Avellaneda", "River Plate	Buenos Aires", "Rosario Central	Rosario", "San Lorenzo	Buenos Aires", "Talleres	Córdoba", "Unión	Santa Fe", "Vélez Sarsfield	Buenos Aires"};
        String[] menNames = new String[]{"Agustin", "Alejo", "Bruno", "Santino", "Daniel", "Pablo", "Mateo ", "Manuel", "Leo", "Martin ", "Pedro", "Juan", "Martin", "Antonio"};
        String[] surnames = new String[]{"Ponce", "Ledesma", "Castillo", "Vega", "Villalba", "Arias", "Navarro", "Barrios", "Soria", "Alvarado", "Lozano", "James", "Basualdo", "Vedoya", "Momesso", "Osimani", "Dorado", "Gomez", "Noni"};

        Tournament t1 = new Tournament("Liga Mayores",8);
        Tournament t2 = new Tournament("Liga Menores",8);
        Tournament t3 = new Tournament("Campeonato",8);

        Tournament t4 = new Tournament("Copa C",8);
        Tournament t5 = new Tournament("Liga Veteranos",8);
        Tournament t6 = new Tournament("Copa del Club",8);

        ArrayList<Tournament> tournaments = new ArrayList<Tournament>();
        tournaments.add(t1);
        tournaments.add(t3);
        tournaments.add(t5);
        tournaments.add(t6);

        ArrayList<String> men = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 18; j++) {
                men.add(menNames[i]+" "+surnames[j]);
            }
        }
        Collections.shuffle(men);

        ArrayList<Team> teams = new ArrayList<Team>();

        for (String teamName : teamNames) {
            Team team = new Team(teamName,8);
            teams.add(team);
        }

        Random rand = new Random();

        for (Tournament tour:tournaments) {
            for (int i = 0; i < 8; i++) {
                Team team = teams.get(i);
                Collections.shuffle(men);
                for (String name: men) {
                    Player newPlayer = new Player(name,rand.nextInt(20000));
                    Player.Properties properties = new Player.Properties(rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10));
                    newPlayer.refresh(properties);
                    try {
                        team.add(newPlayer);
                    } catch (Team.CompleteTeamException ex) {
                        ex.getMessage();
                    }
                }
                teams.remove(i);
                teams.trimToSize();
                tour.addTeam(team);
            }
        }

        Administrator larana = new Administrator("larana");
        larana.addTournament(t1);
        larana.addTournament(t2);
        larana.addTournament(t3);
        Administrator pasion = new Administrator("pasion");
        pasion.addTournament(t4);
        pasion.addTournament(t5);
        pasion.addTournament(t6);

        createAccount(larana);
        createAccount(pasion);
    }

    /*Metodos para manejo de la cuenta*/
    public static boolean accountIsUser() {
        if (account != null)
            return account instanceof  User;
        return false;
    }

    public static void refresh(Map<String,Map<String,Map<String, Player.Properties>>> tournaments) {
        if (account instanceof Administrator)
            ((Administrator) account).refresh(tournaments);
    }

    // el nombre del club deberia er identico al del administrador

    public static Set<Tournament> getTournaments() {
        return ((Administrator)account).getTournaments();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(accounts);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        accounts = (ArrayList)ois.readObject();
    }

    public Account getSignedAccount() {
        return account;
    }
}
