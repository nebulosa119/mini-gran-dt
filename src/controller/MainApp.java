package controller;

import model.*;
import model.exceptions.ExistentNameException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

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
        //simulate();
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
            MainApp.createAlert("Error al guardar en archivo, Mini Gran DT se va a cerrar.").showAndWait();
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
            URL fileUrl = MainApp.class.getResource("/resources/view/" + windowName + ".fxml");
            page = FXMLLoader.load(fileUrl);
        } catch (Exception e) {
            MainApp.createAlert("Error al cargar, Mini Gran DT se va a cerrar.").showAndWait();
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
        s.getIcons().add(new Image(MainApp.class.getResourceAsStream("/resources/media/icon.png")));
        s.setResizable(false);
    }

    /**
     * Main principal.
     */
    public static void main(String[] args) {
        launch(args);
    }

    private static void simulate(){
        String[] teamNames = new String[]{"Sonido Caracol", "Lincoln", "Matambre Reloaded", "C.A. Hay Combate", "Tu Marido", "Cerezas Inocentes", "Piraña", "La Vieja Señora", "Asfalten Kayen", "Ultimo Momento", "El Equipo de Carama", "FC Ronvodwhisky", "Tenedor Libre", "Herederos del Ñoqui", "Savio F.C.", "Pato Criollo", "Extra Brutt", "El Mago y su Jauria", "Colectivo San Juan", "El Nono Michelin", "Submarino Amarilo", "Jamaica Bajo Cero", "Los Borbotones", "No Manzana", "Corta el Pasto", "Furia FC", "La Vino Tinto", "Lineo B", "Te lo Juro por las Nenas", "La Nave Fulbo Clu", "Argentinos Juniors  Buenos Aires", "Belgrano  Córdoba", "Boca Juniors  Buenos Aires", "Chacarita Juniors  Villa Maipú", "Colón  Santa Fe", "Estudiantes  La Plata", "Ferro Carril Oeste  Buenos Aires", "Gimnasia y Esgrima  Jujuy", "Gimnasia y Esgrima  La Plata", "Independiente  Avellaneda", "Instituto  Córdoba", "Lanús  Lanús", "Newell's Old Boys  Rosario", "Racing Club  Avellaneda", "River Plate  Buenos Aires", "Rosario Central  Rosario", "San Lorenzo  Buenos Aires", "Talleres  Córdoba", "Unión  Santa Fe", "Vélez Sarsfield  Buenos Aires"};
        String[] menNames = new String[]{"Agustin", "Alejo", "Bruno", "Santino", "Daniel", "Pablo", "Mateo ", "Manuel", "Leo", "Martin ", "Pedro", "Juan", "Martin", "Antonio"};
        String[] surnames = new String[]{"Ponce", "Ledesma", "Castillo", "Vega", "Villalba", "Arias", "Navarro", "Barrios", "Soria", "Alvarado", "Lozano", "James", "Basualdo", "Vedoya", "Momesso", "Osimani", "Dorado", "Gomez", "Noni"};

        ArrayList<String> men = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 18; j++) {
                men.add(menNames[i]+" "+surnames[j]);
            }
        }
        Collections.shuffle(men);
        int i = 0;
        ArrayList<Administrator> admins = AccountsManager.getAdmins();
        for (Administrator admin:admins) {
            Set<PhysicalTournament> tours = admin.getTournaments();
            for (PhysicalTournament tour:tours) {
                ArrayList<PhysicalTeam> teams = tour.getTeams();
                for (PhysicalTeam team:teams) {
                    try {
                        team.addPlayer(new PhysicalPlayer(men.get(i++)));
                        team.addPlayer(new PhysicalPlayer(men.get(i++)));
                        team.addPlayer(new PhysicalPlayer(men.get(i++)));
                        team.addPlayer(new PhysicalPlayer(men.get(i++)));
                        team.addPlayer(new PhysicalPlayer(men.get(i++)));
                    } catch (ExistentNameException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
