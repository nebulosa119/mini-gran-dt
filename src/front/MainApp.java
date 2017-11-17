package front;

import back.model.*;
import back.model.exceptions.ExistentNameException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;

import static javafx.application.Platform.exit;

/**
 * Main que inicia la aplicación.
 *
 * @author tdorado
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
            MainApp.createAlert("Error al cargar, Mini Gran DT se va a cerrar.\n" + e.getMessage()).showAndWait();
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
            MainApp.createAlert("Error al guardar en archivo, Mini Gran DT se va a cerrar.\n" + e.getMessage()).showAndWait();
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
    public static void setNewScene(String windowName){
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
            MainApp.createAlert("Error al cargar, Mini Gran DT se va a cerrar.\n" + e.getMessage()).showAndWait();
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
        // 50 equipos
        String[] teamNames = new String[]{"Sonido Caracol", "Lincoln", "Matambre Reloaded", "C.A. Hay Combate", "Tu Marido", "Cerezas Inocentes", "Piraña", "La Vieja Señora", "Asfalten Kayen", "Ultimo Momento", "El Equipo de Carama", "FC Ronvodwhisky", "Tenedor Libre", "Herederos del Ñoqui", "Savio F.C.", "Pato Criollo", "Extra Brutt", "El Mago y su Jauria", "Colectivo San Juan", "El Nono Michelin", "Submarino Amarilo", "Jamaica Bajo Cero", "Los Borbotones", "No Manzana", "Corta el Pasto", "Furia FC", "La Vino Tinto", "Lineo B", "Te lo Juro por las Nenas", "La Nave Fulbo Clu", "Argentinos Juniors  Buenos Aires", "Belgrano  Córdoba", "Boca Juniors  Buenos Aires", "Chacarita Juniors  Villa Maipú", "Colón  Santa Fe", "Estudiantes  La Plata", "Ferro Carril Oeste  Buenos Aires", "Gimnasia y Esgrima  Jujuy", "Gimnasia y Esgrima  La Plata", "Independiente  Avellaneda", "Instituto  Córdoba", "Lanús  Lanús", "Newell's Old Boys  Rosario", "Racing Club  Avellaneda", "River Plate  Buenos Aires", "Rosario Central  Rosario", "San Lorenzo  Buenos Aires", "Talleres  Córdoba", "Unión  Santa Fe", "Vélez Sarsfield  Buenos Aires"};
        String[] surnames = new String[]{"Ponce", "Ledesma", "Castillo", "Vega", "Villalba", "Arias", "Navarro", "Barrios", "Soria", "Alvarado", "Lozano", "James", "Basualdo", "Vedoya", "Momesso", "Osimani", "Dorado", "Gomez", "Noni", "Sanchez", "Martinez", "Prezzavento", "Miura", "Jurado", "Martienz de Oz", "Roca", "Alvear"};
        String[] adminNames   = new String[]{"larana", "pasion", "norchamps", "pilarfutbol", "futbolsur"};
        String[] tourNames   = new String[]{"Copa A", "Copa B", "Liga Infantinles", "Liga Veteranos"};
        String[] menNames = new String[]{"Emilio","Zacarías","Bruno","Dámaso","Damián","Emiliano","Gerardo","Facundo","Sixto","Germán","Felipe","Santiago","Javier","Wilfredo","Cristóbal","Simón","Feliciano","Pedro","Julián","Leonardo","Joaquín","Domigo","Fernando","Ignacio","Gregorio","Beltrán","Tobías","Teodoro","Guillermo","David","Agustín","Eduardo","Darío","Lázaro","Ismael","Salvador","Alejo","Jesús","Constantino","Gonzalo","Elías","Federico","Dimas","Tomás","Juan","Félix","Alberto","Francisco","Cayetano","Sebastián","Ernesto","Isaías","Camilo","Valerio","Jorge","Humberto","Enrique","Jonás","Abrahám","Isaac","Tylor","Robert","Kurtis","Bryan","Peter","Cameron","Andy","Benny","Ace","Bruce","Alexander","John","Jeremy","Charlie","Dexter","Dean","Oswald","Mitchell","Howard","Luke","Roger","Donald","Christian","Zac","Monty","Salvatore","Maurizio","Valentino","Paulino","Biagio","Fabiano","Luigi","Lazzaro","Alessandro","Lionardo","Vittorio","Manoel","Giacomo","Emiliano","Donatello","Agostino","Cecilio","Luciano","Nestore","Gabriello","Benedetto","Abelardo","Massimo","Giulio","Pietro","Silvano","Riccardo","Flavio","Damiano","Gioele","Iker","Andoni","Asier","Fermin","Ibai","Jon","Luken","Unai","Patxi","Gaizka","Imanol","Mintxo","Joseba","Endika","Bittor","Nadir","Amir","Shahzad","Jabbar","Rasul","Ahmed","Mohamed","Hadi","Rabi","Karim","Gabir","Nima","Mansur","Hameed","Imad","Saleem","Jafar","Nizar","Aali","Baqir","Asad","Fadi","Basam","Taha","Thamir","Akram","Samir","Mahmud","Abu","Husain"};
        String[] paises = new String[]{"Afganistán","Kabul","Asia","Albania","Tirana","Europa","Alemania","Berlín","Europa","Andorra","Andorra la Vieja","Europa","Angola","Luanda","África","Antigua y Barbuda","Saint John","América","Arabia Saudita","Riad","Asia","Argelia","Argel","África","Argentina","Buenos Aires","América","Armenia","Ereván","Asia","Australia","Canberra","Oceanía","Austria","Viena","Europa","Azerbaiyán","Bakú","Asia","Bahamas","Nasáu","América","Bangladés","Daca","Asia","Barbados","Bridgetown","América","Baréin","Manama","Asia","Bélgica","Bruselas","Europa"};
        ArrayList<String> men = new ArrayList<>();
        for (int i = 0; i < 159; i++) {
            for (int j = 0; j < 26; j++) {
                men.add(menNames[i]+" "+surnames[j]);
            }
        }
        Collections.shuffle(men);

        for (String adminName:adminNames) {
            AccountsManager.createAdmin(adminName);
        }

        Random rand = new Random();

        int j = 0;
        int p = 0;
        ArrayList<Administrator> admins = AccountsManager.getAdmins();
        for (Administrator admin:admins) {
            for (String tourName:tourNames) {
                int maxPlayers = rand.nextInt(7) + 5; // min 5 ,max 5+7
                PhysicalTournament tour = new PhysicalTournament(tourName+" "+admin.getName(),maxPlayers);
                for (int k = 0; k < 6; k++) {
                    j = j == 49 ? 0 : j;
                    p = p == 53 ? 0 : p;
                    String pais = paises[p++];
                    tour.addTeam(new PhysicalTeam(teamNames[j++]+" de "+pais,tour.getMaxPlayers()));
                }
                admin.addTournament(tour);
            }
        }

        int i = 0;
        for (Administrator admin:admins) {
            Set<PhysicalTournament> tours = admin.getTournaments();
            for (PhysicalTournament tour:tours) {
                ArrayList<PhysicalTeam> teams = tour.getTeams();
                int maxPlayers = tour.getMaxPlayers();
                for (PhysicalTeam team:teams) {
                    for (int k = 0; k < maxPlayers; k++) {
                        try {
                            i = i == 14*18 ? 0 : i;
                            PhysicalPlayer.Properties prop = new PhysicalPlayer.Properties();
                            for (int l = 0; l < 7; l++) {
                                prop.setProperty(l,rand.nextInt(25));
                            }
                            PhysicalPlayer player = new PhysicalPlayer(men.get(i++),prop);
                            player.refreshPrice();
                            team.addPlayer(player);
                        } catch (ExistentNameException e) {
                            System.out.println("salta");
                            // está excepcion puede saltar puesto que no tengo tantos nombre habilitados
                        }
                    }
                }
            }
        }

    }

}
