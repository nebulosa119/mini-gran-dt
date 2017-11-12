package Controllers;

import Models.AccountsManager;
import Models.Administrator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static java.lang.Integer.parseInt;

public class AddTournamentController{

    @FXML
    private TextField tournamentTextField;

    @FXML
    private TextField playersTextField;

    @FXML
    private void handleBack(){
        MainApp.getInstance().setScene("adminView");
    }

    @FXML
    private Label error;

    @FXML
    private void handleCreateTournament(){
        String tournamentName = tournamentTextField.getText();
        int maxPlayers = parseInt(playersTextField.getText());
        if(!((Administrator)AccountsManager.getInstance().getAccount()).hasTournament(tournamentName)) {
            if(((Administrator) AccountsManager.getInstance().getAccount()).createTournament(tournamentName, maxPlayers))
                MainApp.getInstance().setScene("adminView");
            else
                error.setVisible(true);
        }
        else
            error.setVisible(true);
    }
}
