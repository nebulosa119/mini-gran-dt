package Controllers;

import Models.*;
import Models.Properties;
import java.io.Serializable;
import java.util.*;

public class AccountsManager implements Serializable{
    private static ArrayList<Account> accounts;
    private static Account account;

    /*Metodos para la clase: manejo de cuentas*/
    public static void loadAccounts() {
        //accounts = (ArrayList<Account>) FileManager.unserializeObject(Types.USER.fileName);// no se como se soluciona
        accounts = new ArrayList<>();
        simulateAccounts();
    }

    public static void save(){
        // (!(accounts == null || accounts.isEmpty())){
        //   String fileName = Types.USER.fileName;
        // System.out.println("guardando en "+fileName);
        //FileManager.serializeObject(accounts,fileName);
        //
        //FileManager.serializeObject(this,Types.USER.fileName);
    }

    public static boolean contains(String username) {
        return getAccount(username) != null;
    }

    public static void createAccount(String username) {
            accounts.add(new User(username));
    }

    public static void createAccount(Account account) {
        accounts.add(account);
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
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

        for (String teamName:teamNames) {
            Team team = new Team(teamName);
            teams.add(team);
        }

        Random rand = new Random();

        for (Tournament tour:tournaments) {
            for (int i = 0; i < 8; i++) {
                Team team = teams.get(i);
                for (String name: men) {
                    Player newPlayer = new Player(name,rand.nextInt(20000));
                    Properties properties = new Properties(rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10));
                    newPlayer.refresh(properties);
                    team.add(newPlayer,tour.getMaxPlayers());
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

    public static void refresh(Map<String,Map<String,Map<String, Properties>>> tournaments) {
        if (account instanceof Administrator)
            ((Administrator) account).refresh(tournaments);
    }

    public static void createNewTournament(String tourName, int maxPlayers) {
        if (account instanceof Administrator)
            ((Administrator)account).addTournament(new Tournament(tourName, maxPlayers));
    }

    static void createNewTournament(Tournament newTournament) { // hay tres getter metodos distintos pero iguales, despues los meto todos en uno
        if (account instanceof Administrator && newTournament != null)
            ((Administrator)account).addTournament(newTournament);
    }

    // el nombre del club deberia er identico al del administrador
    public static Tournament getTournament(String clubName, String tourName) {
        for (Account account:accounts) {
            if (account instanceof Administrator)
                if (account.getName().equals(clubName) && ((Administrator) account).hasTournament(tourName))
                    return ((Administrator) account).getTournament(tourName);
        }
        return null;
    }

    public static Map<String,Set<String>> getAllTournaments() {
        HashMap<String,Set<String>> tourNames= new HashMap<>();
        for (Account account:accounts) {
            if (account instanceof Administrator){
                Administrator admin = (Administrator)account;
                tourNames.put(admin.getName(), admin.getTournamentNames());
            }
        }
        return tourNames;
    }

    public static Team getUserTeam(String tourName) {
        if (account instanceof User)
            return ((User)account).getTeam(tourName);
        return null;
    }

    public static void addUserToTournament(String adminName, String tourName) {
        ((Administrator)getAccount(adminName)).addUser(tourName,(User) account);
    }
}
