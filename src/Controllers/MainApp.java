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
    private static MainApp instance;

    public MainApp(){
        instance = this;
    }

    public static MainApp getInstance(){
        if(instance==null)
            instance = new MainApp();
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("Mini Gran DT");
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/Resources/Media/icon.png")));
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
        Parent page = null;
        try {
            URL fileUrl = MainApp.class.getResource("/Resources/Views/" + windowName + ".fxml");
            page = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("FXML Error.");
        }
        stage.setScene(new Scene(page));
    }
}
