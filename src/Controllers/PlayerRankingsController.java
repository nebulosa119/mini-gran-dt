package Controllers;

import Models.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PlayerRankingsController implements Initializable {
    @FXML
    private Button returnButton;
    @FXML
    private TextArea titleText;
    @FXML
    private ListView usersList;

    private static ArrayList<UserDT> users;
    private static String tournamentName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleText.setEditable(false);
        titleText.setText("Tabla de posiciones \n Torneo: "+tournamentName);
        titleText.setCenterShape(true);
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
