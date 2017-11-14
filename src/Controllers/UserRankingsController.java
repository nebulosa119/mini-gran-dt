package Controllers;

import Models.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import static javafx.scene.control.TableColumn.SortType.ASCENDING;
import static javafx.scene.control.TableColumn.SortType.DESCENDING;

/**
 * Controlador de la de rankings de jugadores en un torneo.
 *
 * @author emiliobasualdo
 */
public class UserRankingsController implements Initializable {

    @FXML
    private Label tournamentLabel;
    @FXML
    private AnchorPane rankingsAnchorPane;

    private static ArrayList<UserDT> users;
    private static String tournamentName;
    private static Tournament tournament;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tournamentLabel.setText(tournamentName);
        if (users != null) {

            TableColumn<UserDT, Integer> rankingColumn = new TableColumn<>("Puesto");
            TableColumn<UserDT, String> userColumn = new TableColumn<>("Usuario");
            TableColumn<UserDT, Integer> pointsColumn = new TableColumn<>("Puntos");

            rankingColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getPoints(tournament)).asObject());
            userColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
            pointsColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getPoints(tournament)).asObject());

            rankingColumn.setMinWidth(100);
            rankingColumn.setMaxWidth(100);
            rankingColumn.setPrefWidth(100);
            userColumn.setMinWidth(200);
            userColumn.setMaxWidth(200);
            userColumn.setPrefWidth(200);
            pointsColumn.setMinWidth(100);
            pointsColumn.setMaxWidth(100);
            pointsColumn.setPrefWidth(100);

            ObservableList<UserDT> data = FXCollections.observableArrayList();
            for(UserDT user : users) {
                data.add(user);
            }

            SortedList<UserDT> sortedData = new SortedList<>(data);
            TableView<UserDT> rankingsTableView = new TableView<>();

            sortedData.comparatorProperty().bind(rankingsTableView.comparatorProperty());
            rankingsTableView.setItems(sortedData);

            rankingsTableView.getSortOrder().addAll(rankingColumn);
            rankingsTableView.getColumns().addAll(rankingColumn, userColumn, pointsColumn);
            
            rankingsTableView.prefHeightProperty().bind(rankingsAnchorPane.heightProperty());
            rankingsTableView.prefWidthProperty().bind(rankingsAnchorPane.widthProperty());
            rankingsAnchorPane.getChildren().add(rankingsTableView);
        }
    }

    static void setInfo(Tournament t){
        tournamentName = t.getName();
        tournament = t;
        users = AccountsManager.getUsersInTournament(t);
    }

}
