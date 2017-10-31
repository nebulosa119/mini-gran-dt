package Controller;

import Model.Player;
import Model.Team;
import Model.Tournament;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class TeamController {//  crea la ventana del user

    @FXML
    private Button exitButton, ruleButton, playerRankingButton, addPlayerButton, removePlayerButton;
    @FXML
    private TabPane teamsTabPanes;

    private User u;
    private Tournament t;

    public void initModel(User u, Tournament t) {
        /**Inicializo variables de instancia*/
        this.u = u;
        this.t = t;

        /**Configuro los tabs*/
        for(Team team : t.getTeams()) {
            Tab tab = new Tab();
            tab.setText(team.getName());

            /**Defino la tabla de jugadores que va adentro del tab particular*/
            TableView<Player> playerTableView = new TableView<>();

            /**Agrego las columnas a la tabla*/
            TableColumn playerName = new TableColumn("Player name");
            TableColumn playerRanking = new TableColumn("Player ranking");
            playerTableView.getColumns().addAll(playerName, playerRanking);

            /**Defino la data que va a ir adrento de la tabla*/
            ObservableList<Player> data = FXCollections.observableArrayList();
            for(Player p : team.getPlayers()) {
                data.add(p);
            }

            /**Asocio los datos con las celdas de la tabla*/

            teamsTabPanes.getTabs().add(tab);
        }
        /**Seteo los listeners de los botones*/
        exitButton.setOnAction(exitHandler);
        ruleButton.setOnAction(ruleHandler);
        playerRankingButton.setOnAction(rankingHandler);
        addPlayerButton.setOnAction(addPlayerHandler);
        removePlayerButton.setOnAction(removePlayerHandler);
    }

    private EventHandler exitHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Guarda y sale*/

        }
    };

    private EventHandler ruleHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Abre la ventana de reglas*/
            Stage aux = getEventStage(event);
            RulesWindow rw = new RulesWindow();
            aux.setScene(new Scene(rw));
        }
    };

    private EventHandler rankingHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Abre la ventana de ranking de los jugadores*/
            Stage aux = getEventStage(event);
            RankingsWindow rw = new RankingsWindow();
            aux.setScene(new Scene(rw));
        }
    };

    private EventHandler addPlayerHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Añade el jugador elegido y decrementa los fondos*/

        }
    };

    private EventHandler removePlayerHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Remueve el jugador elegido y aumenta los fondos*/

        }
    };

    private Stage getEventStage(Event e) {
        return (Stage) ((Node)e.getSource()).getScene().getWindow();
    }

}
