package Controllers;

import Models.Player;
import Models.Team;
import Models.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

public class TeamSelection {
    @FXML
    private Button editTeamButton;
    @FXML
    private ListView<Team> playersByTeamList;

    private User  user;

    public void initialize(User user) {
        this.user = user;

        for (Team team : user.getTeams().values()) {
            Tab tab = new Tab();
            tab.setText(team.getName());

            /**Defino la data que va a ir adrento de la tabla*/
            ObservableList<Team> data = FXCollections.observableArrayList();
            /*for(Team t : user.getTeams()) {
                data.add(t);
            }

            TableView<Player> playerTableView = new TableView<>();
            playerTableView.setItems(data);
            */

        }
    }
}