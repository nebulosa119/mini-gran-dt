package Controller;

import Model.Player;
import Model.Team;
import Model.Tournament;
import Model.User;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;

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

            /**Defino la data que va a ir adrento de la tabla*/
            ObservableList<Player> data = FXCollections.observableArrayList();
            for(Player p : team.getPlayers()) {
                data.add(p);
            }

            /**Defino la tabla de jugadores que va adentro del tab particular*/
            TableView<Player> playerTableView = new TableView<>();
            playerTableView.setItems(data);

            /**Defino las columnas de la tabla*/
            TableColumn<Player, String> playerName = new TableColumn<>("Name");
            TableColumn<Player, Integer> playerRanking = new TableColumn<>("Ranking");
            TableColumn<Player, Boolean> wantPlayer = new TableColumn<>("Check to buy");

            /**Asocio los datos con las celdas de la tabla*/
            playerName.setCellValueFactory(info -> new SimpleStringProperty(info.getValue().getName()));
            playerRanking.setCellValueFactory(info -> (new SimpleIntegerProperty(info.getValue().getRanking())).asObject());
            wantPlayer.setCellFactory(CheckBoxTableCell.forTableColumn(wantPlayer));

            /**Agrego las columnas a la tabla*/
            playerTableView.getColumns().addAll(playerName, playerRanking);

            /**Agrego la tabla al tab*/
            tab.setContent(playerTableView);

            /**Agrego la tab*/
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
            /**Guarda y sale. NO SE COMO FUCKING HACER ESTO DE SERIALIZAR*/

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
            /**Añade al jugador elegido y decrementa los fondos*/
            for(Tab t : teamsTabPanes.getTabs()) {
                for(Player p : (ObservableList<Player>)((TableView)t.getContent())) {
                    /**Agrego el jugaodr al equipo del usuario*/
                    /**
                     *
                     *
                     *
                     * */
                }
            }
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
