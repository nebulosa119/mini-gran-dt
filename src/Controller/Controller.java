package Controller;

import Model.*;
import Model.Properties;
import View.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Controller extends Application {

    private Stage stage;
    private View view;
    private AccountsManager accounts;
    private Account account;
    private String username;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // guardamos el stage para manipularlo desde cualquier parte de la clase
        stage = primaryStage;
        // guardamos los accoutns para manipularlos desde cualqeuir parte
        accounts = simulateTournaments();
        /*accounts = (AccountsManager) FileManager.readObjectFromFile(Types.ADMIN.fileName);
        if (accounts == null) {
            System.out.println("es vacio");
            accounts = new AccountsManager();
        }*/
        // creando login
        /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene loginScene = new Scene(root);*/

        VBox vBox = new VBox();
        Text welcomeTex = new Text("Welcome");
        Button button = new Button("Login/CreateAccount");
        TextField userTextField = new TextField();
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!userTextField.getText().isEmpty()){
                    username = userTextField.getText();
                    loadView(username);
                    setScene(view.createScene());
                }
            }
        });
        vBox.getChildren().addAll(welcomeTex,userTextField,button);
        Scene loginScene = new Scene(vBox,600,600);
        // logeamos
        primaryStage.setTitle("Mini Gran DT");

        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }

    private void loadView(String username) {
         //buscamos cual tiene al usuario
        if (!accounts.contains(username)){
            // si no existe lo creamos como usuario
            System.out.println("no Existe");
            accounts.createAccount(username);
        }
        account = accounts.getAccount(username);
        if (account instanceof User){
            view = new UserView(this);
            System.out.println("es user");
        }else {
            view = new AdminView(this);
            System.out.println("es admin");
        }
    }

    private AccountsManager simulateTournaments() throws Team.PlayerExistsException, Team.CompleteTeamException {
        String[] teamNames = new String[]{
                "Sonido Caracol",
                "Lincoln",
                "Matambre Reloaded",
                "C.A. Hay Combate",
                "Tu Marido",
                "Cerezas Inocentes",
                "Piraña",
                "La Vieja Señora",
                "Asfalten Kayen",
                "Ultimo Momento",
                "El Equipo de Carama",
                "FC Ronvodwhisky",
                "Tenedor Libre",
                "Herederos del Ñoqui",
                "Savio F.C.",
                "Pato Criollo",
                "Extra Brutt",
                "El Mago y su Jauria",
                "Colectivo San Juan",
                "El Nono Michelin",
                "Submarino Amarilo",
                "Jamaica Bajo Cero",
                "Los Borbotones",
                "No Manzana",
                "Corta el Pasto",
                "Furia FC",
                "La Vino Tinto",
                "Lineo B",
                "Te lo Juro por las Nenas",
                "La Nave Fulbo Clu",
                "Argentinos Juniors	Buenos Aires",
                "Belgrano	Córdoba",
                "Boca Juniors	Buenos Aires",
                "Chacarita Juniors	Villa Maipú",
                "Colón	Santa Fe",
                "Estudiantes	La Plata",
                "Ferro Carril Oeste	Buenos Aires",
                "Gimnasia y Esgrima	Jujuy",
                "Gimnasia y Esgrima	La Plata",
                "Independiente	Avellaneda",
                "Instituto	Córdoba",
                "Lanús	Lanús",
                "Newell's Old Boys	Rosario",
                "Racing Club	Avellaneda",
                "River Plate	Buenos Aires",
                "Rosario Central	Rosario",
                "San Lorenzo	Buenos Aires",
                "Talleres	Córdoba",
                "Unión	Santa Fe",
                "Vélez Sarsfield	Buenos Aires"
        };
        String[] menNames = new String[]{
                "Agustin",
                "Alejo",
                "Bruno",
                "Santino",
                "Daniel",
                "Pablo",
                "Mateo ",
                "Manuel",
                "Leo",
                "Martin ",
                "Pedro",
                "Juan",
                "Martin",
                "Antonio"
        };
        String[] surnames = new String[]{
                "Ponce",
                "Ledesma",
                "Castillo",
                "Vega",
                "Villalba",
                "Arias",
                "Navarro",
                "Barrios",
                "Soria",
                "Alvarado",
                "Lozano",
                "James",
                "Basualdo",
                "Vedoya",
                "Momesso",
                "Osimani",
                "Dorado",
                "Gomez",
                "Noni"
        };

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
                    Player newPlayer = new Player(name);
                    Properties properties = new Properties(rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10));
                    newPlayer.refresh(properties);
                    try {
                        team.add(newPlayer,tour.getMaxPlayers());
                    }catch (Team.CompleteTeamException e){
                        //e.printStackTrace();
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

        AccountsManager accountsManager = new AccountsManager();
        accountsManager.createAccount(larana);
        accountsManager.createAccount(pasion);
        //System.out.println(larana);
        //System.out.println();
        //System.out.println();
        //System.out.println(pasion);

        return accountsManager;
    }

    public ArrayList<Tournament> getAccountTournaments() {
        if (account instanceof Administrator)
            return ((Administrator)account).getTournaments();
        return null;
    }

    public void createNewTorunament(String tourName, int maxPlayers) {
        if (account instanceof Administrator)
            ((Administrator)account).addTournament(new Tournament(tourName, maxPlayers));
    }
    private void createNewTorunament(Tournament newTournament) { // hay tres getter metodos distintos pero iguales, despues los meto todos en uno
        if (account instanceof Administrator && newTournament != null)
            ((Administrator)account).addTournament(newTournament);
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

    public static void main(String[] args) {
        launch(args);
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

    //esta todo muy mesclado, despues hat uqe cambiarlo
    public ArrayList<String> getUserTournaments() {
        if (account instanceof User)
            return account.getTournamentNames();
        return null;
    }

    public Team getUserTeam(String tourName) {
        if (account instanceof User)
            return ((User)account).getTeam(tourName);
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

    //private JFXTextField userTextField;

    /*@FXML
    public void handleClose(){
        //accounts.save();
        System.exit(0);
    }

    @FXML
    public void handleLogin(){
        if(!userTextField.getText().isEmpty()){
            username = userTextField.getText();
            loadView(username);
            stage.setScene(view.createScene());
        }
    }*/
}
