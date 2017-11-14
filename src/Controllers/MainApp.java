package Controllers;

import java.io.IOException;
import java.net.URL;
import Models.AccountsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class MainApp extends Application {

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

    @Override
    public void start(Stage primaryStage){
        try {
            AccountsManager.loadAccounts();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading.");
            exit();
        }
        stage = primaryStage;
        stage.setTitle("Mini Gran DT");
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/Resources/Media/icon.png")));
        setScene("login");
        stage.show();
    }

    @Override
    public void stop(){
        try {
            AccountsManager.save();
            super.stop();
        } catch (Exception e) {
            System.out.println("Error while saving.");
        }
    }

    public static void setScene(String windowName){
        Parent page = null;
        try {
            URL fileUrl = MainApp.class.getResource("/Resources/Views/" + windowName + ".fxml");
            page = FXMLLoader.load(fileUrl);
        } catch (Exception e) {
            System.out.println("FXML " + windowName +" loading error.");
        }
        if(page!=null)
            stage.setScene(new Scene(page));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
