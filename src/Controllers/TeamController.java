package Controllers;

import Models.*;
import Models.Exceptions.InsufficientFundsException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TeamController {//  controla la ventana del userDT

    @FXML
    private Button exitButton, ruleButton, playerRankingButton, addPlayerButton, removePlayerButton;
    @FXML
    private ListView<Player> userPlayerList;
    @FXML
    private TabPane teamsTabPanes;
    @FXML
    private Pane teamsToCheckOutPane;
    @FXML
    private Label pointsLabel, fundsLabel;

    private UserDT userDT;
    private static Tournament tournament;

    public void initialize() {
        /**Inicializo variables de instancia*/
        userDT = (UserDT) AccountsManager.getSignedAccount();
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
            TableColumn<Player, Integer> playerPrice = new TableColumn<>("Price");

            /**Asocio los datos con las celdas de la tabla*/
            playerRanking.setCellValueFactory(info -> (new SimpleIntegerProperty(info.getValue().getRanking())).asObject());
            playerPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Player, Integer> param) {
                    return (new SimpleIntegerProperty(param.getValue().getPrice())).asObject();
                }
            });
            playerName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> param) {
                    ObservableValue<String> aux = new SimpleStringProperty(param.getValue().getName());
                    return aux;
                }
            });

            /**Agrego las columnas a la tabla*/
            playerTableView.getColumns().addAll(playerName, playerRanking, playerPrice);

            /**Agrego la tabla al tab*/
            tab.setContent(playerTableView);

            /**Agrego la tab*/
            teamsTabPanes.getTabs().add(tab);

            pointsLabel.setText("Current points: " + Integer.toString(userDT.getUserTeams().getUserTeam(tournament).getUserPoints()));
            fundsLabel.setText("Available funds: " + Integer.toString(userDT.getExpenses().getAvailableFunds(tournament)));
        }
        /**Configuro el listView del usuario*/
        /**Lo lleno con los jugadores que tenga*/
        if(userDT != null && userDT.hasSigned(tournament))
            userPlayerList.setItems(FXCollections.observableArrayList(userDT.getUserTeams().getUserTeamPlayers(tournament)));
        userPlayerList.setCellFactory(param -> new ListCell<Player>() {
                @Override
                protected void updateItem(Player p, boolean empty) { super.updateItem(p, empty);
                if (empty || p == null || p.getName() == null) {
                    setText(null);
                } else {
                    setText(p.getName());
                } }
        });
        /**Permite selecciones múltiples*/
        userPlayerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        VBox aux = new VBox();
        aux.getChildren().add(teamsTabPanes);
        teamsToCheckOutPane.getChildren().add(aux);
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
            MainApp.setScene("login");
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
    private EventHandler addPlayerHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            /**Añade al jugador elegido y decrementa los fondos*/
            try {
                for (Tab t : teamsTabPanes.getTabs()) {
                    for (Player p : (ObservableList<Player>) ((TableView) t.getContent()).getSelectionModel().getSelectedItems()) {
                        /**Agrego el jugaodr al equipo del usuario*/
                        if(!userPlayerList.getItems().contains(p)) {
                            userDT.buy(tournament, p);
                            userPlayerList.getItems().add(p);
                        }
                    }
                }
                fundsLabel.setText("Available funds: " + Integer.toString(userDT.getExpenses().getAvailableFunds(tournament)));
            } catch (InsufficientFundsException e) {
                showErrorMessage();
            }
        }

    };

    private EventHandler removePlayerHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Remueve el jugador elegido y aumenta los fondos*/
            for(Player p : userPlayerList.getSelectionModel().getSelectedItems()) {
                userDT.sell(tournament, p);
                userPlayerList.getItems().removeAll(p);
            }
            fundsLabel.setText("Available funds: " + Integer.toString(userDT.getExpenses().getAvailableFunds(tournament)));
        }
    };

    private void showErrorMessage() {
        Alert aux = new Alert(Alert.AlertType.ERROR);
        aux.setTitle("ERROR");
        aux.setHeaderText("INSUFFICIENT FUNDS OR FULL TEAM");
        aux.showAndWait();
    }

    public static void setTournament(Tournament t) {
        tournament = t;
    }

}
