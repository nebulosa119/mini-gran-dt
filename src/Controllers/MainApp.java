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
        } catch (Exception e) {
            MainApp.createAlert("Error al cargar, Mini Gran DT se va a cerrar.").showAndWait();
            exit();
        }
        stage = primaryStage;
        configureStage(stage);
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
            MainApp.createAlert("Error al guardar en archivo, intente de nuevo.").showAndWait();
            exit();
        }
    }

    /**
     * Cambia el scene del stage principal.
     * @param windowName nombre del archivo fxml de la ventana
     */
    public static void setScene(String windowName){
        Parent page = chargeScene(windowName);
        if(page!=null)
            stage.setScene(new Scene(page));
    }

    /**
     * Crea un stage auxiliar con la scene dada.
     * @param windowName nombre del archivo fxml de la ventana
     */
    static void setNewScene(String windowName){
        Parent page = chargeScene(windowName);
        Stage aux = new Stage();
        configureStage(aux);
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
     * Carga una scene desde un fxml y lanza error si no puede cargarla
     *
     * @param windowName nombre de archivo fxml
     *
     * @return fxml cargado en un Parent
     */
    private static Parent chargeScene(String windowName){
        Parent page = null;
        try {
            URL fileUrl = MainApp.class.getResource("/Resources/Views/" + windowName + ".fxml");
            page = FXMLLoader.load(fileUrl);
        } catch (Exception e) {
            MainApp.createAlert("Error cargando un archivo, por favor intente de nuevo.").showAndWait();
            exit();
        }
        return page;
    }

    /**
     * Configura un Stage para que tenga de título Mini Gran DT y le ponga el icono del juego
     *
     * @param s Stage a usar
     */
    private static void configureStage(Stage s){
        s.setTitle("Mini Gran DT");
        s.getIcons().add(new Image(MainApp.class.getResourceAsStream("/Resources/Media/icon.png")));
        s.setResizable(false);
    }

    /**
     * Main principal.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
