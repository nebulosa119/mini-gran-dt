package Controllers;

import Models.AccountsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
            MainApp.createAlert("Error while loading. GranDT will close.").showAndWait();
            exit();
        }
        stage = primaryStage;
        stage.setTitle("Mini Gran DT");
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/Resources/Media/icon.png")));
        stage.setResizable(false);
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
            MainApp.createAlert("Error while saving data. Please try again.").showAndWait();
            exit();
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
            System.out.println(e.getStackTrace());
            MainApp.createAlert("Error cargando un archivo, por favor intente de nuevo.").showAndWait();
            exit();
        }
        if(page!=null)
            stage.setScene(new Scene(page));
    }

    /**
     * Crea un stage auxiliar con la scene dada.
     * @param windowName nombre del archivo fxml de la ventana
     */
    static void setNewScene(String windowName){
        Parent page = null;
        try {
            URL fileUrl = MainApp.class.getResource("/Resources/Views/" + windowName + ".fxml");
            page = FXMLLoader.load(fileUrl);
        } catch (Exception e) {
            MainApp.createAlert("Hubo un error cargando un archivo, por favor intente de nuevo.").showAndWait();
            exit();
        }
        Stage aux = new Stage();
        aux.setTitle("Mini Gran DT");
        aux.getIcons().add(new Image(MainApp.class.getResourceAsStream("/Resources/Media/icon.png")));
        if(page!=null){
            aux.setScene(new Scene(page));
           aux.show();
        }
    }

    /**
     * Genera una alerta con un mensaje
     *
     * @param message Mensaje de la alerta
     *
     * @return Alerta con mensaje
     */
    public static Alert createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(message);
        return alert;
    }

    /**
     * Main principal.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
