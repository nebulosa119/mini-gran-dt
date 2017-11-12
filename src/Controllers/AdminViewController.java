package Controllers;

import Models.*;
import Models.Exceptions.CompleteTeamException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Callback;

import javax.swing.text.View;
import java.net.URL;
import java.util.*;

import static java.lang.Integer.parseInt;

public class AdminViewController implements Initializable{

    private Team team = null;
    private Tournament actualTournament = null;
    private Tournament expandedTournament = null;
    private TableView<ViewPlayer> playersTableView;

    @FXML
    private Label tournamentsLoaded;

    @FXML
    private Label dataRefreshed;

    @FXML
    private Accordion tournamentsAccordion;

    @FXML
    private AnchorPane playersAnchorPane;

    @FXML
    private Button refreshData;

    @FXML
    private Button addTeam;

    @FXML
    private Button addPlayer;

    @FXML
    private void handleLogout(){
        MainApp.getInstance().setScene("login");
    }

    @FXML
     private void handleRefresh(){
        if(actualTournament == null || team == null) {
            createAlert("Error, ningun equipo seleccionado.").showAndWait();
            return;
        }
        Alert alert = questionAlert("¿Esta seguro que quiere cargar?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            dataRefreshed.setVisible(true);
            uploadData();
        }
    }

    @FXML
    private void handleAddTournament(){
        MainApp.getInstance().setScene("addTournament");
    }

    @FXML
    private void handleAddTeam(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Crear equipo en " + expandedTournament.getName());
        dialog.setHeaderText("Agregar equipo en " + expandedTournament.getName() + ".");
        dialog.setContentText("Ingrese el nombre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
            expandedTournament.addTeam(new Team(result.get(), expandedTournament.getMaxPlayers()));
        MainApp.getInstance().setScene("adminView");
    }

    @FXML
    private void handleAddPlayer(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Crear jugador en " + team.getName());
        dialog.setHeaderText("Agregar jugador en " + team.getName() + ".");
        dialog.setContentText("Ingrese el nombre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
                team.add(new Player(result.get()));
            } catch(CompleteTeamException e) {
                Alert alert = createAlert("Equipo Completo.");
                alert.showAndWait();
            }
        }
        MainApp.getInstance().setScene("adminView");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<Tournament> tournaments = AccountsManager.getInstance().getTournaments();

        for (Tournament tournament: tournaments) {
            ToggleGroup tournamentGroup = new ToggleGroup();
            VBox tournamentBox = new VBox(10);
            tournamentBox.setPadding(new Insets(10));
            for (Team team : tournament.getTeams()) {
                RadioButton teamButton = new RadioButton(team.getName());
                tournamentGroup.getToggles().add(teamButton);
                tournamentBox.getChildren().add(teamButton);
            }
            tournamentGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                    if (tournamentGroup.getSelectedToggle() != null) {
                        refreshData.setVisible(true);
                        addPlayer.setVisible(true);
                        RadioButton selected = (RadioButton)tournamentGroup.getSelectedToggle();
                        actualTournament = tournament;
                        team = tournament.getTeam(selected.getText());
                        showTeamPlayers();
                    }
                }
            });
            TitledPane tournamentPane = new TitledPane(tournament.getName(), tournamentBox);
            tournamentPane.expandedProperty().addListener((ov, b, b1) -> {
                expandedTournament = tournament;
                addTeam.setVisible(true);
            });
            tournamentsAccordion.getPanes().add(tournamentPane);
        }
        tournamentsLoaded.setVisible(true);
    }

    private void showTeamPlayers(){

        TableColumn<ViewPlayer, String> playerNameCol = new TableColumn("Jugador");
        playerNameCol.setMinWidth(150);
        playerNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ViewPlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ViewPlayer, String> param) {
                return param.getValue().name;
            }
        });

        TableColumn<ViewPlayer, String> normalGoalsScored = new TableColumn("Goles anotados");
        normalGoalsScored.setMinWidth(170);
        normalGoalsScored.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ViewPlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ViewPlayer, String> param) {
                return param.getValue().normal_goals_scored;
            }
        });
        normalGoalsScored.setCellFactory(TextFieldTableCell.forTableColumn());
        normalGoalsScored.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ViewPlayer,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ViewPlayer,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setNormalGoalsScored(t.getNewValue());
            }
        });

        TableColumn<ViewPlayer, String> goalsScoredByPenaltyKick = new TableColumn("Goles anotados por penal");
        goalsScoredByPenaltyKick.setMinWidth(230);
        goalsScoredByPenaltyKick.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ViewPlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ViewPlayer, String> param) {
                return param.getValue().goals_scored_by_penalty_kick;
            }
        });
        goalsScoredByPenaltyKick.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsScoredByPenaltyKick.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ViewPlayer,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ViewPlayer,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsScoredByPenaltyKick(t.getNewValue());
            }
        });

        TableColumn<ViewPlayer, String> penaltyCatched = new TableColumn("Penal atrapado");
        penaltyCatched.setMinWidth(140);
        penaltyCatched.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ViewPlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ViewPlayer, String> param) {
                return param.getValue().penalty_catched;
            }
        });
        penaltyCatched.setCellFactory(TextFieldTableCell.forTableColumn());
        penaltyCatched.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ViewPlayer,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ViewPlayer,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setPenaltyCatched(t.getNewValue());
            }
        });

        TableColumn<ViewPlayer, String> goalsScoredGoalkeeper = new TableColumn("Gol anotado por arquero");
        goalsScoredGoalkeeper.setMinWidth(200);
        goalsScoredGoalkeeper.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ViewPlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ViewPlayer, String> param) {
                return param.getValue().goals_scored_goalkeeper;
            }
        });
        goalsScoredGoalkeeper.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsScoredGoalkeeper.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ViewPlayer,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ViewPlayer,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsScoredGoalkeeper(t.getNewValue());
            }
        });

        TableColumn<ViewPlayer, String> yellowCards = new TableColumn("Tarjetas amarillas");
        yellowCards.setMinWidth(120);
        yellowCards.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ViewPlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ViewPlayer, String> param) {
                return param.getValue().yellow_cards;
            }
        });
        yellowCards.setCellFactory(TextFieldTableCell.forTableColumn());
        yellowCards.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ViewPlayer,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ViewPlayer,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setYellowCards(t.getNewValue());
            }
        });

        TableColumn<ViewPlayer, String> redCards = new TableColumn("Tarjetas rojas");
        redCards.setMinWidth(100);
        redCards.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ViewPlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ViewPlayer, String> param) {
                return param.getValue().red_cards;
            }
        });
        redCards.setCellFactory(TextFieldTableCell.forTableColumn());
        redCards.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ViewPlayer,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ViewPlayer,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setRedCards(t.getNewValue());
            }
        });

        TableColumn<ViewPlayer, String> goalsAgainst = new TableColumn("Goles en contra");
        goalsAgainst.setMinWidth(130);
        goalsAgainst.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ViewPlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ViewPlayer, String> param) {
                return param.getValue().goals_against;
            }
        });
        goalsAgainst.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsAgainst.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ViewPlayer,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ViewPlayer,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsAgainst(t.getNewValue());
            }
        });

        //armo lista de players vacios con la info del team
        ArrayList<ViewPlayer> newPlayers = new ArrayList<>();
        for (Player player : team.getPlayers()){
            newPlayers.add(new ViewPlayer(player.getName(), ((Integer)(player.getProperties().getProperty(0))).toString(), ((Integer)player.getProperties().getProperty(1)).toString(), ((Integer)player.getProperties().getProperty(2)).toString(), ((Integer)player.getProperties().getProperty(3)).toString(), ((Integer)player.getProperties().getProperty(4)).toString(), ((Integer)player.getProperties().getProperty(5)).toString(), ((Integer)player.getProperties().getProperty(6)).toString() ));
        }
        ObservableList<ViewPlayer> data = FXCollections.observableArrayList(newPlayers);
        for(ViewPlayer vp : data) {
            System.out.println(vp.getName());
            System.out.println(vp.getGoalsAgainst());
            System.out.println(vp.getGoalsScoredByPenaltyKick());
            System.out.println(vp.getGoalsScoredGoalkeeper());
            System.out.println(vp.getNormalGoalsScored());
            System.out.println(vp.getPenaltyCatched());
            System.out.println(vp.getRedCards());
            System.out.println(vp.getYellowCards());
        }
        playersAnchorPane.getChildren().removeAll();
        playersTableView = new TableView<ViewPlayer>();
        playersTableView.setItems(data);
        playersTableView.setEditable(true);

        playersTableView.getColumns().addAll(playerNameCol,normalGoalsScored,goalsScoredByPenaltyKick,penaltyCatched,goalsScoredGoalkeeper,yellowCards,redCards,goalsAgainst);
        playersTableView.prefHeightProperty().bind(playersAnchorPane.heightProperty());
        playersAnchorPane.getChildren().add(playersTableView);
    }

    private void uploadData(){
        Map<String,Map<String,Map<String, Player.Properties>>> dataTournaments = new HashMap<>();
        dataTournaments.put(actualTournament.getName(), getTournamentData());
        AccountsManager.refresh(dataTournaments);
    }

    private Map<String,Map<String, Player.Properties>> getTournamentData(){
        Map<String,Map<String, Player.Properties>> dataTournament = new HashMap<>();
        dataTournament.put(team.getName(), getTeamData());
        return dataTournament;
    }

    private Map<String, Player.Properties> getTeamData() {
        Map<String, Player.Properties> dataTeam = new HashMap<>();
        Player.Properties prop = new Player.Properties();
        for (Object item : playersTableView.getItems()) {
            String name = ((ViewPlayer)item).getName();
            prop.setProperty(0, parseInt(((ViewPlayer)item).getNormalGoalsScored()));
            prop.setProperty(1, parseInt(((ViewPlayer)item).getGoalsScoredByPenaltyKick()));
            prop.setProperty(2, parseInt(((ViewPlayer)item).getPenaltyCatched()));
            prop.setProperty(3, parseInt(((ViewPlayer)item).getGoalsScoredGoalkeeper()));
            prop.setProperty(4, parseInt(((ViewPlayer)item).getYellowCards()));
            prop.setProperty(5, parseInt(((ViewPlayer)item).getRedCards()));
            prop.setProperty(6, parseInt(((ViewPlayer)item).getGoalsAgainst()));
            dataTeam.put(name,prop);
        }
        return dataTeam;
    }

    private Alert createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(message);
        return alert;
    }

    private Alert questionAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(message);
        return alert;
    }

    private static class ViewPlayer {
        private final SimpleStringProperty name;
        private final SimpleStringProperty normal_goals_scored;
        private final SimpleStringProperty goals_scored_by_penalty_kick;
        private final SimpleStringProperty penalty_catched;
        private final SimpleStringProperty goals_scored_goalkeeper;
        private final SimpleStringProperty yellow_cards;
        private final SimpleStringProperty red_cards;
        private final SimpleStringProperty goals_against;

        private ViewPlayer(String name, String normal_goals_scored, String goals_scored_by_penalty_kick, String penalty_catched, String goals_scored_goalkeeper, String yellow_cards, String red_cards, String goals_against) {
            this.name = new SimpleStringProperty(name);
            this.normal_goals_scored = new SimpleStringProperty(normal_goals_scored);
            this.goals_scored_by_penalty_kick = new SimpleStringProperty(goals_scored_by_penalty_kick);
            this.penalty_catched = new SimpleStringProperty(penalty_catched);
            this.goals_scored_goalkeeper = new SimpleStringProperty(goals_scored_goalkeeper);
            this.yellow_cards = new SimpleStringProperty(yellow_cards);
            this.red_cards = new SimpleStringProperty(red_cards);
            this.goals_against = new SimpleStringProperty(goals_against);
        }

        String getName() {
            return name.get();
        }

        String getNormalGoalsScored() {
            return normal_goals_scored.get();
        }

        String getGoalsScoredByPenaltyKick() {
            return goals_scored_by_penalty_kick.get();
        }

        String getPenaltyCatched() {
            return penalty_catched.get();
        }

        String getGoalsScoredGoalkeeper() {
            return goals_scored_goalkeeper.get();
        }

        String getYellowCards() {
            return yellow_cards.get();
        }

        String getRedCards() {
            return red_cards.get();
        }

        String getGoalsAgainst() {
            return goals_against.get();
        }

        void setNormalGoalsScored(String normal_goals_scored) {
            this.normal_goals_scored.set(normal_goals_scored);
        }

        void setGoalsScoredByPenaltyKick(String goals_scored_by_penalty_kick) {
            this.goals_scored_by_penalty_kick.set(goals_scored_by_penalty_kick);
        }

        void setPenaltyCatched(String penalty_catched) {
            this.penalty_catched.set(penalty_catched);
        }

        void setGoalsScoredGoalkeeper(String goals_scored_goalkeeper) {
            this.goals_scored_goalkeeper.set(goals_scored_goalkeeper);
        }

        void setYellowCards(String yellow_cards) {
            this.yellow_cards.set(yellow_cards);
        }

        void setRedCards(String red_cards) {
            this.red_cards.set(red_cards);
        }

        void setGoalsAgainst(String goals_against) {
            this.goals_against.set(goals_against);
        }
    }
}
