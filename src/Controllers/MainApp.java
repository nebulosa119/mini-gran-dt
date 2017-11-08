package Controllers;

import java.net.URL;

import Models.AccountsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application { // saquemoslo de Controlles despues

    private static Stage stage;
    //private static HashMap<String,Scene> scenes;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("Mini Gran DT");
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/Resources/Media/icon.png")));
        // si no se cargan que se cierre el app y listo
        /*scenes = new HashMap<>();
        scenes.put("addPlayerStats",FileManager.loadFxml("addPlayerStats"));
        scenes.put("login",FileManager.loadFxml("login"));
        scenes.put("adminView",FileManager.loadFxml("adminView"));
        scenes.put("registrationScene",FileManager.loadFxml("registrationScene"));
        scenes.put("manageTournaments",FileManager.loadFxml("manageTournaments"));
        scenes.put("playerRankings",FileManager.loadFxml("playerRankings"));
        scenes.put("teamManager",FileManager.loadFxml("teamManager"));
        scenes.put("teamSelection",FileManager.loadFxml("teamSelection"));
        scenes.put("transitionScene",FileManager.loadFxml("transitionScene"));*/

        AccountsManager.loadAccounts();
        setScene("login");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        //accounts.save();
        super.stop();
    }

    public static void setScene(String windowName){
        //stage.setScene(scenes.get(windowName));
        Parent page = null;
        try {
            URL fileUrl = MainApp.class.getResource("/resources/views/" + windowName + ".fxml");
            page = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("FXML Error.");
        }
        stage.setScene(new Scene(page));
    }

    public void accountLogout(){
        setScene("login");
    }

}
