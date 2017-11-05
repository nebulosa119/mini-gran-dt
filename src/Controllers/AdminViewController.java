package Controllers;

import Models.Administrator;
import Models.Properties;
import Models.Team;
import Models.Tournament;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.util.*;

public class AdminViewController extends ViewController {
    private Administrator admin;

    AdminViewController(MainApp controller, Administrator admin) {
        super(controller);
        this.admin = admin;
    }

    @Override
    public Scene createMainWindow(){
        Button createTournamentButton = new Button("Create Tournament");
        createTournamentButton.setMaxWidth(Double.MAX_VALUE);
        createTournamentButton.setOnAction(event -> show(createTextInputDialog("New Tournament", "Input the tournament information","Tournament name","Players per team")));

        Button loadDataButton = new Button("Load Data To Tournaments");
        loadDataButton.setMaxWidth(Double.MAX_VALUE);
        loadDataButton.setOnAction(event -> controller.setScene(createLoadDataScene()));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(createTournamentButton,loadDataButton);
        return new Scene(hBox,600,50);
    }

    private Scene createLoadDataScene(){
        Accordion tAccordion = createTournamentsView(admin.getTournaments());
        tAccordion.setMinSize(150, 100);

        Button confirmButton = new Button("Confirm");
        confirmButton.setMaxWidth(Double.MAX_VALUE);
        confirmButton.setOnAction(event -> {
            Alert alert = createAlert("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                uploadData(tAccordion);// como quedaria mejor esto?
            }else
                alert.close();
        });

        Button backButton = new Button("Cancel");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setOnAction(event -> controller.setScene(createMainWindow()));

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(backButton,confirmButton);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tAccordion,buttonBox);
        return new Scene(vBox,600,600);
    }

    private Accordion createTournamentsView(Set<Tournament> tournaments){
        Accordion tourAccordion = new Accordion();

        for (Tournament tournament: tournaments) {
            Accordion teamAccordion = new Accordion();

            for (Team team : tournament.getTeams()) {
                TableView table = createPlayersTable(team);
                teamAccordion.getPanes().add(new TitledPane(team.getName(),table));
            }
            tourAccordion.getPanes().add(new TitledPane(tournament.getName(),teamAccordion));
        }
        return tourAccordion;
    }

    private TableView createPlayersTable(Team team){

        TableColumn playerNameCol = new TableColumn("Player");
        playerNameCol.setMinWidth(200);
        playerNameCol.setCellValueFactory(new PropertyValueFactory<Player,String>("name"));

        TableColumn normalGoalsScored = new TableColumn("Normal Goals Scored");
        normalGoalsScored.setMinWidth(170);
        normalGoalsScored.setCellValueFactory(new PropertyValueFactory<Player,String>("normal_goals_scored"));
        normalGoalsScored.setCellFactory(TextFieldTableCell.forTableColumn());
        normalGoalsScored.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNormalGoalsScored(t.getNewValue());
            }
        });

        TableColumn goalsScoredByPenaltyKick = new TableColumn("Goals Scored By Penalty Kick");
        goalsScoredByPenaltyKick.setMinWidth(230);
        goalsScoredByPenaltyKick.setCellValueFactory(new PropertyValueFactory<Player,String>("goals_scored_by_penalty_kick"));
        goalsScoredByPenaltyKick.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsScoredByPenaltyKick.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGoalsScoredByPenaltyKick(t.getNewValue());
            }
        });

        TableColumn penaltyCatched = new TableColumn("Penalty Catched");
        penaltyCatched.setMinWidth(140);
        penaltyCatched.setCellValueFactory(new PropertyValueFactory<Player,String>("penalty_catched"));
        penaltyCatched.setCellFactory(TextFieldTableCell.forTableColumn());
        penaltyCatched.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPenaltyCatched(t.getNewValue());
            }
        });

        TableColumn goalsScoredGoalkeeper = new TableColumn("Goals Scored Goalkeeper");
        goalsScoredGoalkeeper.setMinWidth(200);
        goalsScoredGoalkeeper.setCellValueFactory(new PropertyValueFactory<Player,String>("goals_scored_goalkeeper"));
        goalsScoredGoalkeeper.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsScoredGoalkeeper.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGoalsScoredGoalkeeper(t.getNewValue());
            }
        });

        TableColumn yellowCards = new TableColumn("Yellow Cards");
        yellowCards.setMinWidth(120);
        yellowCards.setCellValueFactory(new PropertyValueFactory<Player,String>("yellow_cards"));
        yellowCards.setCellFactory(TextFieldTableCell.forTableColumn());
        yellowCards.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setYellowCards(t.getNewValue());
            }
        });

        TableColumn redCards = new TableColumn("Red Cards");
        redCards.setMinWidth(100);
        redCards.setCellValueFactory(new PropertyValueFactory<Player,String>("red_cards"));
        redCards.setCellFactory(TextFieldTableCell.forTableColumn());
        redCards.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setRedCards(t.getNewValue());
            }
        });

        TableColumn goalsAgainst = new TableColumn("Goals Against");
        goalsAgainst.setMinWidth(130);
        goalsAgainst.setCellValueFactory(new PropertyValueFactory<Player,String>("goals_against"));
        goalsAgainst.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsAgainst.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player,String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGoalsAgainst(t.getNewValue());
            }
        });

        //armo lista de players vacios con la info del team
        ArrayList<Player> newPlayers = new ArrayList<>();
        for (Models.Player player : team.getPlayers())
            newPlayers.add(new Player(player.getName()));
        ObservableList<Player> data = FXCollections.observableArrayList(newPlayers);

        TableView<Player> tableView = new TableView<Player>();
        tableView.setItems(data);
        tableView.setEditable(true);
        tableView.getColumns().addAll(playerNameCol,normalGoalsScored,goalsScoredByPenaltyKick,penaltyCatched,goalsScoredGoalkeeper,yellowCards,redCards,goalsAgainst);
        return tableView;

    }

    private void uploadData(Accordion tAccordion) {
        Map<String,Map<String,Map<String,Properties>>> dataTournaments = new HashMap<>();
        // para cada acordion de torneo...
        for (TitledPane tourPane : tAccordion.getPanes()) {
            String tourName = tourPane.getText();
            Accordion teamsAccordion = (Accordion)tourPane.getContent();
            dataTournaments.put(tourName,getTournamentData(teamsAccordion));
        }
        AccountsManager.refresh(dataTournaments);
        System.out.println();
    }

    private Map<String,Map<String,Properties>> getTournamentData(Accordion teamsAccordion) {
        Map<String,Map<String,Properties>> dataTournament = new HashMap<>();
        // para cada panel de equipo dentro del accordion del torneo...
        for (TitledPane teamPane : teamsAccordion.getPanes()) {
            String teamName = teamPane.getText();
            TableView teamTable = (TableView)teamPane.getContent();
            dataTournament.put(teamName,getTeamData(teamTable));
        }
        return dataTournament;
    }

    private Map<String,Properties> getTeamData(TableView teamTable) {
        Map<String,Properties> dataTeam = new HashMap<>();
        Properties prop = new Properties();
        for (Object item : teamTable.getItems()) {
            String name = ((Player)item).getName();
            prop.setProperty(0,Integer.parseInt(((Player)item).getNormalGoalsScored()));
            prop.setProperty(1,Integer.parseInt(((Player)item).getGoalsScoredByPenaltyKick()));
            prop.setProperty(2,Integer.parseInt(((Player)item).getPenaltyCatched()));
            prop.setProperty(3,Integer.parseInt(((Player)item).getGoalsScoredGoalkeeper()));
            prop.setProperty(4,Integer.parseInt(((Player)item).getYellowCards()));
            prop.setProperty(5,Integer.parseInt(((Player)item).getRedCards()));
            prop.setProperty(6,Integer.parseInt(((Player)item).getGoalsAgainst()));
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

    private Dialog createTextInputDialog(String title, String message, String textField1, String textField2) {
        Dialog<Tournament> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(message);
        dialog.setResizable(true);

        Label label1 = new Label(textField1);
        Label label2 = new Label(textField2);
        TextField text1 = new TextField();
        TextField text2 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk && Integer.parseInt(text2.getText()) > 0) {
                return new Tournament(text1.getText(), Integer.parseInt(text2.getText()));
            }
            return null;
        });
        return dialog;
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

    public static void show(Dialog textInputDialog) {
        Optional<Tournament> result = textInputDialog.showAndWait();
        result.ifPresent(AccountsManager::createNewTournament);
    }
}
