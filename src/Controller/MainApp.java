package Controller;

import java.util.*;
import Model.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.*;
import View.AdminView;
import View.UserView;
import View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Stage stage;
    private Account account;
    private AccountsManager accounts;
    private View view;

    private static MainApp instance;


    public MainApp() {
        instance = this;
    }

    public static MainApp getInstance() {
        return instance;
    }

    public void createNewTorunament(String tourName, int maxPlayers) {
        if (account instanceof Administrator)
            ((Administrator)account).addTournament(new Tournament(tourName, maxPlayers));
    }
    private void createNewTorunament(Tournament newTournament) { // hay tres getter metodos distintos pero iguales, despues los meto todos en uno
        if (account instanceof Administrator && newTournament != null)
            ((Administrator)account).addTournament(newTournament);
    }

    //lista de torneos del admin
    public ArrayList getAccountTournaments() {
        if (account instanceof Administrator)
            return ((Administrator)account).getTournaments();//lista de torneos
        else if (account instanceof User)
            return account.getTournamentNames(); //lista de string
        return null;
    }

    // generalizar estos metodos
    public ArrayList<String> getUserTournaments() {
        if (account instanceof User)
            return account.getTournamentNames();
        return null;
    }

    // el nombre del club deberia er identico al del administrador
    public Tournament getTournament(String clubName, String tourName) {
        for (Account account:accounts.getAccounts()) {
            if (account instanceof Administrator)
                if (account.getName().equals(clubName) && ((Administrator) account).hasTournament(tourName))
                    return ((Administrator) account).getTournament(tourName);
        }
        return null;
    }

    public Map<String,ArrayList<String>> getAllTournaments() {
        HashMap<String,ArrayList<String>> tourNames= new HashMap<>();
        for (Account account:accounts.getAccounts()) {
            if (account instanceof Administrator){
                Administrator admin = (Administrator)account;
                tourNames.put(admin.getName(), account.getTournamentNames());
            }
        }
        return tourNames;
    }

    public Team getUserTeam(String tourName) {
        if (account instanceof User)
            return ((User)account).getTeam(tourName);
        return null;
    }

    public void show(Dialog textInputDialog) {
        Optional<Tournament> result = textInputDialog.showAndWait();
        result.ifPresent(newTournament -> createNewTorunament(newTournament));
    }

    @Override
    public void stop() throws Exception {
        //accounts.save();
        super.stop();
    }


    private AccountsManager simulateTournaments() throws Team.PlayerExistsException, Team.CompleteTeamException {
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

        AccountsManager accountsManager = new AccountsManager();
        accountsManager.createAccount(larana);
        accountsManager.createAccount(pasion);

        return accountsManager;
    }

    public void refresh(ArrayList<Tournament> tournaments) {
        if (account instanceof Administrator)

            account.refresh(tournaments);
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        accounts = simulateTournaments();
        try {
            stage = primaryStage;
            //stage.initStyle(StageStyle.UNDECORATED);
            gotoLogin();
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Account getAccount() {
        return account;
    }

    public boolean accountLogging(String username){
        //buscamos cual tiene al usuario
        if (!accounts.contains(username)){
            // si no existe lo creamos como usuario
            accounts.createAccount(username);
            System.out.println("no Existe");
        }
        account = accounts.getAccount(username);
        if (account instanceof User){
            gotoUserView();
            System.out.println("es user");
            return true;
        }else {
            gotoAdminView();
            System.out.println("es admin");
            return true;
        }
        //return false;
    }

    public void accountLogout(){
        account = null;
        gotoLogin();
    }

    private void gotoAdminView() {
        /*try {
            replaceSceneContent("profile.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Controller.MainApp2.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        view = new AdminView(this, (Administrator) account);
        setScene(view.createMainWindow());
    }

    private void gotoUserView() {
        /*try {
            replaceSceneContent("profile.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Controller.MainApp2.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        view = new UserView(this);
        setScene(view.createMainWindow());
    }

    private void gotoLogin() {
        try {
            replaceSceneContent("login.fxml");
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(MainApp.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page);
        stage.setScene(scene);
        return page;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
