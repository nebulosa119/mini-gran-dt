package Controllers;

import Models.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

import static javafx.scene.control.TableColumn.SortType.ASCENDING;

/**
 * Controlador de la de rankings de jugadores en un torneo.
 *
 * @author tdorado
 */
public class UserRankingsController implements Initializable {

    @FXML
    private Label tournamentLabel;
    @FXML
    private AnchorPane rankingsAnchorPane;

    private static ArrayList<DT> users;
    private static String tournamentName;
    private static PhysicalTournament physicalTournament;
    private static Map<DT,Integer> usersMap;

    /**
     * Coloca a todos los usuarios del torneo en la tabla.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tournamentLabel.setText(tournamentName);
        if (users != null) {

            TableColumn<DT, Integer> rankingColumn = new TableColumn<>("Puesto");
            TableColumn<DT, String> userColumn = new TableColumn<>("Usuario");
            TableColumn<DT, Integer> pointsColumn = new TableColumn<>("Puntos");

            rankingColumn.setCellValueFactory(param -> new SimpleIntegerProperty(getRanking(param.getValue())).asObject());
            userColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
            pointsColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getPoints(physicalTournament)).asObject());

            rankingColumn.setMinWidth(100);
            rankingColumn.setMaxWidth(100);
            rankingColumn.setPrefWidth(100);
            userColumn.setMinWidth(200);
            userColumn.setMaxWidth(200);
            userColumn.setPrefWidth(200);
            pointsColumn.setMinWidth(100);
            pointsColumn.setMaxWidth(100);
            pointsColumn.setPrefWidth(100);

            ObservableList<DT> data = FXCollections.observableArrayList();
            data.addAll(users);

            TableView<DT> rankingsTableView = new TableView<>();
            rankingsTableView.setItems(data);
            rankingColumn.setSortType(ASCENDING);
            rankingsTableView.getSortOrder().add(rankingColumn);
            rankingsTableView.getColumns().addAll(rankingColumn, userColumn, pointsColumn);
            rankingsTableView.sort();
            
            rankingsTableView.prefHeightProperty().bind(rankingsAnchorPane.heightProperty());
            rankingsTableView.prefWidthProperty().bind(rankingsAnchorPane.widthProperty());
            rankingsAnchorPane.getChildren().add(rankingsTableView);
        }
    }

    /**
     * Setea la información de un torneo para generar el ranking.
     *
     * @param t Torneo a usar
     */
    static void setInfo(PhysicalTournament t){
        tournamentName = t.getName();
        physicalTournament = t;
        users = AccountsManager.getDTsInTournament(t);
        usersMap = setRanking();
    }

    /**
     * Genera un mapa que tiene los puestos de los usuarios.
     *
     * @return Mapa con usuarios como key y su puesto como value
     */
    private static Map<DT, Integer> setRanking() {
        Map<DT,Integer> map = new HashMap<>();
        for (int i=0; i<users.size(); i++) {
            map.put(users.get(i), i+1);
        }
        return map;
    }

    /**
     * Da el puesto de un usuario.
     *
     * @param user Usuario
     *
     * @return Puesto del usuario
     */
    private int getRanking(DT user) {
        return usersMap.get(user);
    }
}
