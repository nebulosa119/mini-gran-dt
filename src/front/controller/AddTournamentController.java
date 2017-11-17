package front.controller;

import back.model.AccountsManager;
import back.model.Administrator;
import back.model.PhysicalTournament;
import front.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static java.lang.Integer.parseInt;

/**
 * Controlador de la vista que agrega torneos del administrador.
 *
 * @author tdorado
 */
public class AddTournamentController{

    @FXML
    private TextField tournamentTextField;
    @FXML
    private TextField playersTextField;
    @FXML
    private Label error;

    /**
     * Permite al administrador volver a la ventana anterior
     */
    @FXML
    private void handleBack(){
        MainApp.setScene("adminView");
    }

    /**
     * Permite al admin crearo un torneo
     * Se le va a pedir el nombre del torneo y cantidad de jugadores en el mismo
     */
    @FXML
    private void handleCreateTournament(){
        String tournamentName = tournamentTextField.getText();
        int maxPlayers = parseInt(playersTextField.getText());
        if(!((Administrator)AccountsManager.getSignedAccount()).containsTournament(new PhysicalTournament(tournamentName,maxPlayers))) {
            if(((Administrator) AccountsManager.getSignedAccount()).createTournament(tournamentName, maxPlayers))
                MainApp.setScene("adminView");
            else
                error.setVisible(true);
        }
        else
            error.setVisible(true);
    }
}
