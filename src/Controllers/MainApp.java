package Controllers;

import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application { // saquemoslo de Controlles despues

    private static MainApp instance;
    private static Stage stage;
    private static HashMap<String,Scene> scenes;

    public MainApp() {
        instance = this;
    } //No modificar esto!

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        // si no se cargan que se cierre el app y listo
        scenes = new HashMap<>();
        scenes.put("addPlayerStats",FileManager.loadFxml("addPlayerStats"));
        scenes.put("login",FileManager.loadFxml("login"));
        scenes.put("ManageTournaments",FileManager.loadFxml("ManageTournaments"));
        scenes.put("PlayerRankings",FileManager.loadFxml("PlayerRankings"));
        scenes.put("TeamManager",FileManager.loadFxml("TeamManager"));
        scenes.put("TeamSelection",FileManager.loadFxml("TeamSelection"));
        scenes.put("TransitionScene",FileManager.loadFxml("TransitionScene"));

        AccountsManager.loadAccounts();
        setScene("login");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        //accounts.save();
        super.stop();
    }

    public static MainApp getInstance() {
        return instance;
    }

    public static void setScene(String windowName){
        stage.setScene(scenes.get(windowName));
    }

    //Agrego este metodo hasta terminar de implementar lo demas, solo para que compile y funcione
    public static void setScene(Scene scene){
        stage.setScene(scene);
    }

    public void accountLogout(){
        setScene("login");
    }

}
