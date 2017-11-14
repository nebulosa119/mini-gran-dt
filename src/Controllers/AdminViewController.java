package Controllers;

import Models.*;
import Models.Exceptions.CompleteTeamException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import java.net.URL;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Controlador de la vista del administrador.
 *
 * @author tdorado
 */
public class AdminViewController implements Initializable{

    private Team team = null;
    private Tournament actualTournament = null;
    private Tournament expandedTournament = null;
    private TableView<ViewPlayer> playersTableView;
    private Map<Tournament, VBox> tournamentVBoxMap = new HashMap<>();
    private Map<Tournament, ToggleGroup> tournamentToggleGroupMap = new HashMap<>();

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

    /**
     * Permite al admin desloguearse
     */
    @FXML
    private void handleLogout(){
        MainApp.setScene("login");
    }

    /**
     * Maneja la subida de datos de jugadores.
     * Se le pregunta al admin si esta seguro de lo que está por subir y se llama el metodo que levanta la informacion
     */
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

    /**
     * Presenta la ventana de agregado de torneo
     */
    @FXML
    private void handleAddTournament(){
        MainApp.setScene("addTournament");
    }

    /**
     * Presenta la ventana de agregado de equipos
     */
    @FXML
    private void handleAddTeam(){
        if(expandedTournament==null){
            Alert alert = createAlert("Ningun torneo seleccionado.");
            alert.showAndWait();
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Crear equipo en " + expandedTournament.getName());
        dialog.setHeaderText("Agregar equipo en " + expandedTournament.getName() + ".");
        dialog.setContentText("Ingrese el nombre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Team toAddTeam = new Team(result.get(), expandedTournament.getMaxPlayers());
            if(expandedTournament.hasTeam(toAddTeam)) {
                Alert alert = createAlert("El equipo " + toAddTeam.getName() + " ya existe.");
                alert.showAndWait();
                return;
            }
            expandedTournament.addTeam(toAddTeam);
            RadioButton aux = new RadioButton();
            aux.setText(toAddTeam.getName());
            tournamentToggleGroupMap.get(expandedTournament).getToggles().add(aux);
            tournamentVBoxMap.get(expandedTournament).getChildren().add(aux);
        }
    }

    /**
     * Presenta la ventana de agregado de jugador.
     * Si el equipo se encuntre completo se le advierte al administrador
     */
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
                playersTableView.getItems().add(new ViewPlayer(result.get()));
            } catch(CompleteTeamException e) {
                Alert alert = createAlert("Equipo Completo.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Carga los respectivos datos de la ventana
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<Tournament> tournaments = AccountsManager.getTournaments();

        for (Tournament tournament: tournaments) {
            ToggleGroup tournamentGroup = new ToggleGroup();
            VBox tournamentBox = new VBox(10);
            tournamentBox.setPadding(new Insets(10));
            tournamentToggleGroupMap.put(tournament, tournamentGroup);
            tournamentVBoxMap.put(tournament, tournamentBox);
            for (Team team : tournament.getTeams()) {
                RadioButton teamButton = new RadioButton(team.getName());
                tournamentGroup.getToggles().add(teamButton);
                tournamentBox.getChildren().add(teamButton);
            }
            tournamentGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                if (tournamentGroup.getSelectedToggle() != null) {
                    refreshData.setVisible(true);
                    addPlayer.setVisible(true);
                    RadioButton selected = (RadioButton)tournamentGroup.getSelectedToggle();
                    actualTournament = tournament;
                    team = tournament.getTeam(selected.getText());
                    showTeamPlayers();
                    playersAnchorPane.setVisible(true);
                }
            });
            TitledPane tournamentPane = new TitledPane(tournament.getName(), tournamentBox);
            tournamentsAccordion.getPanes().add(tournamentPane);
            tournamentsAccordion.expandedPaneProperty().addListener((ov, b, b1) -> {
                if(tournamentsAccordion.getExpandedPane()!=null){
                    if(tournamentPane.isExpanded()){
                        expandedTournament = tournament;
                        addTeam.setVisible(true);
                        if (tournamentGroup.getSelectedToggle() != null) {
                            refreshData.setVisible(true);
                            addPlayer.setVisible(true);
                            playersAnchorPane.setVisible(true);
                            RadioButton selected = (RadioButton)tournamentGroup.getSelectedToggle();
                            actualTournament = tournament;
                            team = tournament.getTeam(selected.getText());
                            showTeamPlayers();
                        }
                    }
                }else{
                    playersAnchorPane.setVisible(false);
                    refreshData.setVisible(false);
                    addPlayer.setVisible(false);
                    addTeam.setVisible(false);
                }
            });
        }
        tournamentsLoaded.setVisible(true);
    }

    /**
     * Carga la informacion de cada jugador
     */
    private void showTeamPlayers(){

        TableColumn<ViewPlayer, String> playerNameCol = new TableColumn<>("Jugador");
        playerNameCol.setCellValueFactory(param -> param.getValue().name);

        TableColumn<ViewPlayer, String> normalGoalsScored = new TableColumn<>("Goles anotados");
        normalGoalsScored.setCellValueFactory(param -> param.getValue().normal_goals_scored);
        normalGoalsScored.setCellFactory(TextFieldTableCell.forTableColumn());
        normalGoalsScored.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setNormalGoalsScored(t.getNewValue()));

        TableColumn<ViewPlayer, String> goalsScoredByPenaltyKick = new TableColumn<>("Goles de penal");
        goalsScoredByPenaltyKick.setCellValueFactory(param -> param.getValue().goals_scored_by_penalty_kick);
        goalsScoredByPenaltyKick.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsScoredByPenaltyKick.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsScoredByPenaltyKick(t.getNewValue()));

        TableColumn<ViewPlayer, String> penaltyCatched = new TableColumn<>("Penal atrapado");
        penaltyCatched.setCellValueFactory(param -> param.getValue().penalty_catched);
        penaltyCatched.setCellFactory(TextFieldTableCell.forTableColumn());
        penaltyCatched.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setPenaltyCatched(t.getNewValue()));

        TableColumn<ViewPlayer, String> goalsScoredGoalkeeper = new TableColumn<>("Gol de arquero");
        goalsScoredGoalkeeper.setCellValueFactory(param -> param.getValue().goals_scored_goalkeeper);
        goalsScoredGoalkeeper.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsScoredGoalkeeper.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsScoredGoalkeeper(t.getNewValue()));

        TableColumn<ViewPlayer, String> yellowCards = new TableColumn<>("Tarjetas amarillas");
        yellowCards.setCellValueFactory(param -> param.getValue().yellow_cards);
        yellowCards.setCellFactory(TextFieldTableCell.forTableColumn());
        yellowCards.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setYellowCards(t.getNewValue()));

        TableColumn<ViewPlayer, String> redCards = new TableColumn<>("Tarjetas rojas");
        redCards.setCellValueFactory(param -> param.getValue().red_cards);
        redCards.setCellFactory(TextFieldTableCell.forTableColumn());
        redCards.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setRedCards(t.getNewValue()));

        TableColumn<ViewPlayer, String> goalsAgainst = new TableColumn<>("Goles en contra");
        goalsAgainst.setCellValueFactory(param -> param.getValue().goals_against);
        goalsAgainst.setCellFactory(TextFieldTableCell.forTableColumn());
        goalsAgainst.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsAgainst(t.getNewValue()));

        ArrayList<ViewPlayer> newPlayers = new ArrayList<>();
        for (Player player : team.getPlayers()){
            newPlayers.add(new ViewPlayer(player.getName(), ((Integer)(player.getProperties().getProperty(0))).toString(), ((Integer)player.getProperties().getProperty(1)).toString(), ((Integer)player.getProperties().getProperty(2)).toString(), ((Integer)player.getProperties().getProperty(3)).toString(), ((Integer)player.getProperties().getProperty(4)).toString(), ((Integer)player.getProperties().getProperty(5)).toString(), ((Integer)player.getProperties().getProperty(6)).toString() ));
        }
        ObservableList<ViewPlayer> data = FXCollections.observableArrayList(newPlayers);

        playersAnchorPane.getChildren().removeAll();
        playersTableView = new TableView<>();
        playersTableView.setItems(data);
        playersTableView.setEditable(true);

        playersTableView.getColumns().addAll(playerNameCol,normalGoalsScored,goalsScoredByPenaltyKick,penaltyCatched,goalsScoredGoalkeeper,yellowCards,redCards,goalsAgainst);
        playersTableView.prefHeightProperty().bind(playersAnchorPane.heightProperty());
        playersTableView.prefWidthProperty().bind(playersAnchorPane.widthProperty());
        playersAnchorPane.getChildren().add(playersTableView);
    }

    /**
     * Refresca los puntos de cada jugador a AccountsManager
     */
    private void uploadData(){
        Map<String,Map<String,Map<String, Player.Properties>>> dataTournaments = new HashMap<>();
        dataTournaments.put(actualTournament.getName(), getTournamentData());
        ((Administrator)AccountsManager.getSignedAccount()).refresh(dataTournaments);
    }

    /**
     * Genera el archivo de equipo que refresca la informacion de los jugadores
     *
     * @return Devuelve un mapa, nombre de equipo de key y otro mapa de value
     */
    private Map<String,Map<String, Player.Properties>> getTournamentData(){
        Map<String,Map<String, Player.Properties>> dataTournament = new HashMap<>();
        dataTournament.put(team.getName(), getTeamData());
        System.out.println(dataTournament);
        return dataTournament;
    }

    /**
     * Genera el archivo de equipo que refresca la informacion de cada jugador
     *
     * @return Devuelve un mapa, nombre de jugador de key y sus propiedades de value
     */
    private Map<String, Player.Properties> getTeamData() {
        Map<String, Player.Properties> dataTeam = new HashMap<>();
        for (Object item : playersTableView.getItems()) {
            Player.Properties prop = new Player.Properties();
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

    /**
     * Genera una alerta con un mensaje
     *
     * @param message Mensaje de la alerta
     *
     * @return Alerta con mensaje
     */
    private Alert createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(message);
        return alert;
    }

    /**
     * Genera una alerta con una pregunta
     *
     * @param question Pregunta de la alerta
     *
     * @return Alerta con pregunta
     */
    private Alert questionAlert(String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(question);
        return alert;
    }

    /**
     * Clase que contiene las propiedades de un jugador con el formato para colocarlo en una TableView
     *
     * @author emiliobasualdo
     */
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

        private ViewPlayer(String name) {
            this.name = new SimpleStringProperty(name);
            normal_goals_scored = new SimpleStringProperty("0");
            goals_scored_by_penalty_kick = new SimpleStringProperty("0");
            penalty_catched = new SimpleStringProperty("0");
            goals_scored_goalkeeper = new SimpleStringProperty("0");
            yellow_cards = new SimpleStringProperty("0");
            red_cards = new SimpleStringProperty("0");
            goals_against= new SimpleStringProperty("0");
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
