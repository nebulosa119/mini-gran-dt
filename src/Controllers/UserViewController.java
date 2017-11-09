package Controllers;

import Models.AccountsManager;
import Models.Team;
import Models.Tournament;
import Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
//import sun.plugin.javascript.navig.Anchor;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class UserViewController implements Initializable{

    private Team team = null;

    @FXML
    private Accordion tournamentAccordion;
    @FXML
    private Button signUpButton;
    @FXML
    private AnchorPane userTeamView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<Tournament> tournaments = AccountsManager.getInstance().getTournaments();

        for(Tournament tournament : tournaments) {
            ToggleGroup tournamentGroup = new ToggleGroup();
            VBox tournamentBox = new VBox(10);
            tournamentBox.setPadding(new Insets(10));
            for(Team team : tournament.getTeams()) {
                RadioButton teamButton = new RadioButton(team.getName());
                tournamentGroup.getToggles().add(teamButton);
                tournamentBox.getChildren().add(teamButton);
            }
            tournamentGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if(tournamentGroup.getSelectedToggle() != null && ((User)AccountsManager.getInstance().getSignedAccount()).getTeam(tournament.getName()) != null) {
                        RadioButton selected = (RadioButton)tournamentGroup.getSelectedToggle();
                        team = tournament.getTeam(selected.getText());
                        TeamController.setTournament(tournament);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("teamManager.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        userTeamView.getChildren().add(root);
                    }
                }
            });
            TitledPane tournamentPane = new TitledPane(tournament.getName(), tournamentBox);
            tournamentAccordion.getPanes().add(tournamentPane);
        }

    }
}
