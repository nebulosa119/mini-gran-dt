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

/**
 * Main que inicia la aplicación.
 *
 * @author emiliobasualdo
 */
public class MainApp extends Application {

    private static Stage stage;

    /**
     * Realiza la carga de datos del archivo y llama al login.
     */
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

    /**
     * Se guarda automaticamente al cerrar la aplicación.
     */
    @Override
    public void stop(){
        try {
            AccountsManager.save();
            super.stop();
        } catch (Exception e) {
            System.out.println("Error while saving.");
        }
    }

    /**
     * Cambia el scene del stage principal.
     * @param windowName nombre del archivo fxml de la ventana
     */
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

    public static void setNewScene(String windowName){
        Parent page = null;
        try {
            URL fileUrl = MainApp.class.getResource("/Resources/Views/" + windowName + ".fxml");
            page = FXMLLoader.load(fileUrl);
        } catch (Exception e) {
            System.out.println("FXML " + windowName +" loading error.");
        }
        Stage aux = new Stage();
        if(page!=null){
            aux.setScene(new Scene(page));
            aux.show();
        }
    }

    /**
     * Main principal.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
