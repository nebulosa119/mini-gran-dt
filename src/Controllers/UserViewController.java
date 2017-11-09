package Controllers;

import Models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
//import sun.plugin.javascript.navig.Anchor;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UserViewController implements Initializable{

    private RadioButton selectedButton = null;

    private Map<RadioButton, Tournament> map = new HashMap<>();

    @FXML
    private Accordion tournamentAccordion;
    @FXML
    private Button signUpButton;
    @FXML
    private AnchorPane userTeamView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(Administrator administrator : AccountsManager.getInstance().getAdmins()) {
            ToggleGroup tournamentGroup = new ToggleGroup();
            VBox tournamentBox = new VBox(10);
            tournamentBox.setPadding(new Insets(10));
            for(Tournament tournament : administrator.getTournaments()) {
                RadioButton tButton = new RadioButton(tournament.getName());
                map.put(tButton, tournament);
                tournamentGroup.getToggles().add(tButton);
                tournamentBox.getChildren().add(tButton);
            }
            tournamentGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if(tournamentGroup.getSelectedToggle() != null &&
                            ((User)AccountsManager.getInstance().getSignedAccount()).getTeam(((RadioButton)tournamentGroup.getSelectedToggle()).getText()) != null) {
                        RadioButton selected = (RadioButton)tournamentGroup.getSelectedToggle();
                        Tournament aux = administrator.getTournament(selected.getText());
                        TeamController.setTournament(aux);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("teamManager.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        userTeamView.getChildren().add(root);
                    } else {
                        selectedButton = (RadioButton) tournamentGroup.getSelectedToggle();
                    }
                }
            });
            TitledPane tournamentPane = new TitledPane(administrator.getName(), tournamentBox);
            tournamentAccordion.getPanes().add(tournamentPane);
        }

        signUpButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(selectedButton != null) {
                    TeamController.setTournament(map.get(selectedButton));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/Views/teamManager.fxml"));
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

    }
}
