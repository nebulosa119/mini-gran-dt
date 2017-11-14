package Controllers;

import Models.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PlayerRankingsController implements Initializable {

    @FXML
    private Label tournamentLabel;
    @FXML
    private ListView usersList;

    private static ArrayList<UserDT> users;
    private static String tournamentName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tournamentLabel.setText(tournamentName);
        if (users != null)
            usersList.setItems(FXCollections.observableArrayList(users));
    }

    static void setInfo(Tournament tournament){
        tournamentName = tournament.getName();
        users = AccountsManager.getUsersInTournament(tournament);
        users.sort(Comparator.comparingInt(UserDT::getPoints));
    }

    @FXML
    private void returnHandler(){
        MainApp.setScene("userView");
    }

}
