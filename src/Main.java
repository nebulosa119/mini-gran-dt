import Controller.AccountsMananger;
import Controller.AdminMananger;
import Controller.Types;
import Model.Account;
import Model.Administrator;
import Model.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    private Stage stage;
    AccountsMananger accounts;
    private Scene currentScene;
    private String username;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // guardamos el stage para manipularlo desde cualquier parte de la clase
        stage = primaryStage;
        // guardamos los accoutns para manipularlos desde cualqeuir parte
        accounts = new AccountsMananger();
        accounts.loadAccounts();
        // creando login
        Scene loginScene = new Scene(createLogInWindow(), 300, 275);

        // logeamos
        primaryStage.setScene(loginScene);
        primaryStage.show();

        /*Button confrimButton = new Button("Confirm");
        confrimButton.setOnAction(handleCloseButtonAction());

        Parent root1 = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Mini Gran DT");

        ArrayList<Tournament> tournaments = new ArrayList<Tournament>();
        Team team = new Team("cracks", 5);
        try {
            team.add(new Player("juanjo"));
        } catch (Team.CompleteTeamException e) {
            e.printStackTrace();
        } catch (Team.PlayerExistsException e) {
            e.printStackTrace();
        }
        Tournament t1 = new Tournament("mayores");
        t1.addTeam(team);
        Tournament t2 = new Tournament("juveniles");
        t2.addTeam(team);
        tournaments.add(t1);
        tournaments.add(t2);

        AdminMananger atdu = new AdminMananger(tournaments);
        VBox root2 = atdu.createVBox();
        root2.getChildren().add(confrimButton);
        primaryStage.setScene(new Scene(root2, 300, 275));
        primaryStage.showAndWait();*/
    }


    private VBox createLogInWindow(){
        TextField wellcomeTextField = new TextField("Welcome to Mini Gran DT");
        wellcomeTextField.setEditable(false);

        TextField userTextField = new TextField("Username");

        Button loginButton = new Button("LogIn/Create Account");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene nextScene = createNextWindow(userTextField.getText());
                stage.setScene(nextScene);
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(wellcomeTextField,userTextField,loginButton);

        return vBox;
    }

    private Scene createNextWindow(String username) {
        Scene returnScene;
        // buscamos cual tiene al usuario
        if (!accounts.contains(username)){
            // si no existe lo creamos como usuario
            accounts.createAccount(username, Types.USER);
        }
        Account account = accounts.getAccount(username);
        if (account instanceof Administrator){// esto quedo feo, hay que arreglarlo
            //creando window para el admin
            Administrator admin = (Administrator) account;
            AdminMananger adminM = new AdminMananger(admin.getTournaments());
            returnScene = new Scene(adminM.createVBox(), 600, 600);// crea un vBox con la informacion de los torneos
        }else{
            // creando ventana para el usuario
            User user = (User) account;
            UserMananger userM = new UserMananger(user.getTeams());
            returnScene = new Scene(userM.createVbox(), 600, 600);
        }
        return returnScene;
    }

    @Override
    public void stop() throws Exception {
        accounts.save();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
