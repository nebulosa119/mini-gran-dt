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
    @FXML
    private ListView<Player> userPlayerList;

    private User user;
    private Tournament tournament;

    public void initModel(User user, Tournament tournament) {
        /**Inicializo variables de instancia*/
        this.user = user;
        this.tournament = tournament;
        teamsTabPanes = new TabPane();

        /**Configuro los tabs*/
        for(Team team : tournament.getTeams()) {
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

            /**Configuro el listView del usuario*/
            /**Lo lleno con los jugadores que tenga*/
            userPlayerList.setItems(FXCollections.observableArrayList(user.getTeam(tournament.getName()).getPlayers()));
            userPlayerList.setCellFactory(param -> new ListCell<Player>() {
                @Override
                protected void updateItem(Player p, boolean empty) {
                    super.updateItem(p, empty);
                    if (empty || p == null || p.getName() == null) {
                        setText(null);
                    } else {
                        setText(p.getName());
                    }
                }
            });
            /**Permite selecciones múltiples*/
            userPlayerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
            Stage aux = new Stage();
            RulesWindow rw = new RulesWindow();
            aux.setScene(new Scene(rw));
            aux.show();
        }
    };

    private EventHandler rankingHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Abre la ventana de ranking de los jugadores*/
            Stage aux = new Stage();
            RankingsWindow rw = new RankingsWindow();
            aux.setScene(new Scene(rw));
        }
    };

    /**En cuanto al tema de repetidos: se debería ver desde el model eso. Es decir, en la clase Usuario debería existir un método para verificar si
     * el jugador que quiere y puede comprar es repetido o no*/
    private EventHandler addPlayerHandler = new EventHandler(){
        @Override
        public void handle(Event event) {
            /**Añade al jugador elegido y decrementa los fondos*/
            for(Tab t : teamsTabPanes.getTabs()) {
                for(Player p : (ObservableList<Player>)((TableView)t.getContent()).getSelectionModel().getSelectedItems()) {
                    /**Agrego el jugaodr al equipo del usuario*/
                    if(user.canBuy(p) && user.hasCapacity(tournament)) {
                        user.getTeam(tournament.getName()).getPlayers().add(p);
                    } else {
                        showErrorMessage();
                    }
                    /**Se debería desde acá restarle al usuario sus fondos*/
                }
            }
        }
    };

    private EventHandler removePlayerHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Remueve el jugador elegido y aumenta los fondos*/
            for(Player p : userPlayerList.getSelectionModel().getSelectedItems()) {
                user.sell(p); /**Implementar este método en User*/
                user.getTeam(tournament.getName()).getPlayers().remove(p);
            }
        }
    };

    private Stage getEventStage(Event e) {
        return (Stage) ((Node)e.getSource()).getScene().getWindow();
    }

    private void showErrorMessage() {
        Alert aux = new Alert(Alert.AlertType.ERROR);
        aux.setTitle("ERROR");
        aux.setHeaderText("INSUFFICIENT FUNDS OR FULL TEAM");
        aux.showAndWait();
    }

}
