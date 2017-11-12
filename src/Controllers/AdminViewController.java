package Controllers;

import Models.*;
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

import java.net.URL;
import java.util.*;

public class AdminViewController implements Initializable{

    private Team team = null;
    private String tournamentName = null;
    private String teamName = null;
    private TableView<Player> playersTableView;

    @FXML
    private Label tournamentsLoaded;

    @FXML
    private Label dataRefreshed;

    @FXML
    private Accordion tournamentsAccordion;

    @FXML
    private AnchorPane playersAnchorPane;

    @FXML
    private void handleLogout(){
        MainApp.getInstance().setScene("login");
    }

        @FXML
     private void handleRefresh(){
        if(tournamentName == null || teamName == null)
            return;
        dataRefreshed.setVisible(true);
        uploadData();
    }

    private void uploadData(){
        Map<String,Map<String,Map<String, Models.Player.Properties>>> dataTournaments = new HashMap<>();
        dataTournaments.put(tournamentName,getTournamentData());
        AccountsManager.refresh(dataTournaments);
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
                        RadioButton selected = (RadioButton)tournamentGroup.getSelectedToggle();
                        team = tournament.getTeam(selected.getText());
                        tournamentName = tournament.getName();
                        teamName = team.getName();
                        showTeamPlayers();
                    }
                }
            });
            TitledPane tournamentPane = new TitledPane(tournament.getName(), tournamentBox);
            tournamentsAccordion.getPanes().add(tournamentPane);
        }
        tournamentsLoaded.setVisible(true);
    }

    private void showTeamPlayers(){
        //armo lista de players vacios con la info del team
        ArrayList<Player> newPlayers = new ArrayList<>();
        for (Models.Player player : team.getPlayers()){
            newPlayers.add(new Player(player.getName()));

        }
        ObservableList<Player> data = FXCollections.observableArrayList(newPlayers);

        TableColumn playerNameCol = new TableColumn("Jugador");
        playerNameCol.setMinWidth(150);
        playerNameCol.setCellValueFactory(new PropertyValueFactory<Player,String>("name"));

        TableColumn normalGoalsScored = new TableColumn("Goles anotados");
        normalGoalsScored.setMinWidth(170);
        normalGoalsScored.setCellValueFactory(new PropertyValueFactory<Player,String>("normal_goals_scored"));
        normalGoalsScored.setCellFactory(TextFieldTableCell.forTableColumn());
        normalGoalsScored.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setNormalGoalsScored(t.getNewValue());
            }
        });

        TableColumn goalsScoredByPenaltyKick = new TableColumn("Goles anotados por penal");
        goalsScoredByPenaltyKick.setMinWidth(230);
        goalsScoredByPenaltyKick.setCellValueFactory(new PropertyValueFactory<Player,String>("goals_scored_by_penalty_kick"));
        goalsScoredByPenaltyKick.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsScoredByPenaltyKick.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsScoredByPenaltyKick(t.getNewValue());
            }
        });

        TableColumn penaltyCatched = new TableColumn("Penal atrapado");
        penaltyCatched.setMinWidth(140);
        penaltyCatched.setCellValueFactory(new PropertyValueFactory<Player,String>("penalty_catched"));
        penaltyCatched.setCellFactory(TextFieldTableCell.forTableColumn());
        penaltyCatched.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setPenaltyCatched(t.getNewValue());
            }
        });

        TableColumn goalsScoredGoalkeeper = new TableColumn("Gol anotado por arquero");
        goalsScoredGoalkeeper.setMinWidth(200);
        goalsScoredGoalkeeper.setCellValueFactory(new PropertyValueFactory<Player,String>("goals_scored_goalkeeper"));
        goalsScoredGoalkeeper.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsScoredGoalkeeper.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsScoredGoalkeeper(t.getNewValue());
            }
        });

        TableColumn yellowCards = new TableColumn("Tarjetas amarillas");
        yellowCards.setMinWidth(120);
        yellowCards.setCellValueFactory(new PropertyValueFactory<Player,String>("yellow_cards"));
        yellowCards.setCellFactory(TextFieldTableCell.forTableColumn());
        yellowCards.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setYellowCards(t.getNewValue());
            }
        });

        TableColumn redCards = new TableColumn("Tarjetas rojas");
        redCards.setMinWidth(100);
        redCards.setCellValueFactory(new PropertyValueFactory<Player,String>("red_cards"));
        redCards.setCellFactory(TextFieldTableCell.forTableColumn());
        redCards.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setRedCards(t.getNewValue());
            }
        });

        TableColumn goalsAgainst = new TableColumn("Goles en contra");
        goalsAgainst.setMinWidth(130);
        goalsAgainst.setCellValueFactory(new PropertyValueFactory<Player,String>("goals_against"));
        goalsAgainst.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsAgainst.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsAgainst(t.getNewValue());
            }
        });

        playersAnchorPane.getChildren().removeAll();
        playersTableView = new TableView<Player>();
        playersTableView.setItems(data);
        playersTableView.setEditable(true);
        playersTableView.getColumns().addAll(playerNameCol,normalGoalsScored,goalsScoredByPenaltyKick,penaltyCatched,goalsScoredGoalkeeper,yellowCards,redCards,goalsAgainst);
        playersTableView.prefHeightProperty().bind(playersAnchorPane.heightProperty());
        playersAnchorPane.getChildren().add(playersTableView);
    }

    private Map<String,Map<String, Models.Player.Properties>> getTournamentData(){
        /*VBox tournamentVBox = (VBox)tournamentsAccordion.getExpandedPane().getContent();
        ToggleGroup tournamentGroup = (ToggleGroup)tournamentVBox.getChildren();
        RadioButton selectedTeam = (RadioButton)tournamentGroup.getSelectedToggle();
        String teamName = selectedTeam.getText();*/
        Map<String,Map<String, Models.Player.Properties>> dataTournament = new HashMap<>();
        dataTournament.put(teamName, getTeamData());
        return dataTournament;
    }

    private Map<String, Models.Player.Properties> getTeamData() {
        Map<String, Models.Player.Properties> dataTeam = new HashMap<>();
        Models.Player.Properties prop = new Models.Player.Properties();
        for (Object item : playersTableView.getItems()) {
            String name = ((AdminViewController.Player)item).getName();
            prop.setProperty(0,Integer.parseInt(((AdminViewController.Player)item).getNormalGoalsScored()));
            prop.setProperty(1,Integer.parseInt(((AdminViewController.Player)item).getGoalsScoredByPenaltyKick()));
            prop.setProperty(2,Integer.parseInt(((AdminViewController.Player)item).getPenaltyCatched()));
            prop.setProperty(3,Integer.parseInt(((AdminViewController.Player)item).getGoalsScoredGoalkeeper()));
            prop.setProperty(4,Integer.parseInt(((AdminViewController.Player)item).getYellowCards()));
            prop.setProperty(5,Integer.parseInt(((AdminViewController.Player)item).getRedCards()));
            prop.setProperty(6,Integer.parseInt(((AdminViewController.Player)item).getGoalsAgainst()));
            dataTeam.put(name,prop);
        }
        return dataTeam;
    }

    /*private void uploadData(Accordion tAccordion) {
        Map<String,Map<String,Map<String, Models.Player.Properties>>> dataTournaments = new HashMap<>();
        // para cada acordion de torneo...
        for (TitledPane tourPane : tAccordion.getPanes()) {
            String tourName = tourPane.getText();
            Accordion teamsAccordion = (Accordion)tourPane.getContent();
            dataTournaments.put(tourName,getTournamentData(teamsAccordion));
        }
        AccountsManager.refresh(dataTournaments);
        System.out.println();
    }

    private Map<String,Map<String, Models.Player.Properties>> getTournamentData(Accordion teamsAccordion) {
        Map<String,Map<String, Models.Player.Properties>> dataTournament = new HashMap<>();
        // para cada panel de equipo dentro del accordion del torneo...
        for (TitledPane teamPane : teamsAccordion.getPanes()) {
            String teamName = teamPane.getText();
            TableView teamTable = (TableView)teamPane.getContent();
            dataTournament.put(teamName,getTeamData(teamTable));
        }
        return dataTournament;
    }

    private Map<String, Models.Player.Properties> getTeamData(TableView teamTable) {
        Map<String, Models.Player.Properties> dataTeam = new HashMap<>();
        Models.Player.Properties prop = new Models.Player.Properties();
        for (Object item : teamTable.getItems()) {
            String name = ((AdminViewController.Player)item).getName();
            prop.setProperty(0,Integer.parseInt(((AdminViewController.Player)item).getNormalGoalsScored()));
            prop.setProperty(1,Integer.parseInt(((AdminViewController.Player)item).getGoalsScoredByPenaltyKick()));
            prop.setProperty(2,Integer.parseInt(((AdminViewController.Player)item).getPenaltyCatched()));
            prop.setProperty(3,Integer.parseInt(((AdminViewController.Player)item).getGoalsScoredGoalkeeper()));
            prop.setProperty(4,Integer.parseInt(((AdminViewController.Player)item).getYellowCards()));
            prop.setProperty(5,Integer.parseInt(((AdminViewController.Player)item).getRedCards()));
            prop.setProperty(6,Integer.parseInt(((AdminViewController.Player)item).getGoalsAgainst()));
            dataTeam.put(name,prop);
        }
        return dataTeam;
    }*/

    private Alert createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(message);
        return alert;
    }

    public static class Player {
        private final SimpleStringProperty name;
        private final SimpleStringProperty normal_goals_scored = new SimpleStringProperty("0");
        private final SimpleStringProperty goals_scored_by_penalty_kick = new SimpleStringProperty("0");
        private final SimpleStringProperty penalty_catched = new SimpleStringProperty("0");
        private final SimpleStringProperty goals_scored_goalkeeper = new SimpleStringProperty("0");
        private final SimpleStringProperty yellow_cards = new SimpleStringProperty("0");
        private final SimpleStringProperty red_cards = new SimpleStringProperty("0");
        private final SimpleStringProperty goals_against = new SimpleStringProperty("0");

        private Player(String name) {
            this.name = new SimpleStringProperty(name);
        }

        public String getName() {
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
