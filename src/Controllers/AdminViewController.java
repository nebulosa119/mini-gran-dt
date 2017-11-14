package Controllers;

import Models.*;
import Models.Exceptions.CompleteTeamException;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Controlador de la vista del administrador.
 *
 * @author tdorado
 */
public class AdminViewController implements Initializable{

    private PhysicalTeam team = null;
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
            PhysicalTeam toAddTeam = new PhysicalTeam(result.get(), expandedTournament.getMaxPlayers());
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
            for (PhysicalTeam team : tournament.getTeams()) {
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
        playerNameCol.setCellValueFactory(param -> (new SimpleStringProperty(param.getValue().getName())));

        TableColumn<ViewPlayer, Integer> normalGoalsScored = new TableColumn<>("Goles anotados");
        normalGoalsScored.setCellValueFactory(param -> (new SimpleIntegerProperty(param.getValue().getNormalGoalsScored())).asObject());
        normalGoalsScored.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        normalGoalsScored.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setNormalGoalsScored(t.getNewValue()));

        TableColumn<ViewPlayer, Integer> goalsScoredByPenaltyKick = new TableColumn<>("Goles de penal");
        goalsScoredByPenaltyKick.setCellValueFactory(param -> (new SimpleIntegerProperty(param.getValue().getGoalsScoredByPenaltyKick())).asObject());
        goalsScoredByPenaltyKick.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        goalsScoredByPenaltyKick.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsScoredByPenaltyKick(t.getNewValue()));

        TableColumn<ViewPlayer, Integer> penaltyCatched = new TableColumn<>("Penal atrapado");
        penaltyCatched.setCellValueFactory(param -> (new SimpleIntegerProperty(param.getValue().getPenaltyCatched())).asObject());
        penaltyCatched.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        penaltyCatched.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setPenaltyCatched(t.getNewValue()));

        TableColumn<ViewPlayer, Integer> goalsScoredGoalkeeper = new TableColumn<>("Gol de arquero");
        goalsScoredGoalkeeper.setCellValueFactory(param -> (new SimpleIntegerProperty(param.getValue().getPenaltyCatched())).asObject());
        goalsScoredByPenaltyKick.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        goalsScoredGoalkeeper.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsScoredGoalkeeper(t.getNewValue()));

        TableColumn<ViewPlayer, Integer> yellowCards = new TableColumn<>("Tarjetas amarillas");
        yellowCards.setCellValueFactory(param -> (new SimpleIntegerProperty(param.getValue().getYellowCards())).asObject());
        yellowCards.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        yellowCards.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setYellowCards(t.getNewValue()));

        TableColumn<ViewPlayer, Integer> redCards = new TableColumn<>("Tarjetas rojas");
        redCards.setCellValueFactory(param -> (new SimpleIntegerProperty(param.getValue().getRedCards())).asObject());
        redCards.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        redCards.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setRedCards(t.getNewValue()));

        TableColumn<ViewPlayer, Integer> goalsAgainst = new TableColumn<>("Goles en contra");
        goalsAgainst.setCellValueFactory(param -> (new SimpleIntegerProperty(param.getValue().getGoalsAgainst())).asObject());
        goalsAgainst.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        goalsAgainst.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setGoalsAgainst(t.getNewValue()));

        ArrayList<ViewPlayer> newPlayers = new ArrayList<>();
        for (Player player : team.getPlayers()){
            newPlayers.add(new ViewPlayer(player.getName(), player.getProperties().getProperty(0), player.getProperties().getProperty(1), player.getProperties().getProperty(2), player.getProperties().getProperty(3), player.getProperties().getProperty(4), player.getProperties().getProperty(5), player.getProperties().getProperty(6)));
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
            prop.setProperty(0, ((ViewPlayer)item).getNormalGoalsScored());
            prop.setProperty(1, ((ViewPlayer)item).getGoalsScoredByPenaltyKick());
            prop.setProperty(2, ((ViewPlayer)item).getPenaltyCatched());
            prop.setProperty(3, ((ViewPlayer)item).getGoalsScoredGoalkeeper());
            prop.setProperty(4, ((ViewPlayer)item).getYellowCards());
            prop.setProperty(5, ((ViewPlayer)item).getRedCards());
            prop.setProperty(6, ((ViewPlayer)item).getGoalsAgainst());
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
        private final SimpleIntegerProperty normal_goals_scored;
        private final SimpleIntegerProperty goals_scored_by_penalty_kick;
        private final SimpleIntegerProperty penalty_catched;
        private final SimpleIntegerProperty goals_scored_goalkeeper;
        private final SimpleIntegerProperty yellow_cards;
        private final SimpleIntegerProperty red_cards;
        private final SimpleIntegerProperty goals_against;

        private ViewPlayer(String name, Integer normal_goals_scored, Integer goals_scored_by_penalty_kick, Integer penalty_catched, Integer goals_scored_goalkeeper, Integer yellow_cards, Integer red_cards, Integer goals_against) {
            this.name = new SimpleStringProperty(name);
            this.normal_goals_scored = new SimpleIntegerProperty(normal_goals_scored);
            this.goals_scored_by_penalty_kick = new SimpleIntegerProperty(goals_scored_by_penalty_kick);
            this.penalty_catched = new SimpleIntegerProperty(penalty_catched);
            this.goals_scored_goalkeeper = new SimpleIntegerProperty(goals_scored_goalkeeper);
            this.yellow_cards = new SimpleIntegerProperty(yellow_cards);
            this.red_cards = new SimpleIntegerProperty(red_cards);
            this.goals_against = new SimpleIntegerProperty(goals_against);
        }

        private ViewPlayer(String name) {
            this.name = new SimpleStringProperty(name);
            normal_goals_scored = new SimpleIntegerProperty(0);
            goals_scored_by_penalty_kick = new SimpleIntegerProperty(0);
            penalty_catched = new SimpleIntegerProperty(0);
            goals_scored_goalkeeper = new SimpleIntegerProperty(0);
            yellow_cards = new SimpleIntegerProperty(0);
            red_cards = new SimpleIntegerProperty(0);
            goals_against= new SimpleIntegerProperty(0);
        }

        String getName() {
            return name.get();
        }

        Integer getNormalGoalsScored() {
            return normal_goals_scored.get();
        }

        Integer getGoalsScoredByPenaltyKick() {
            return goals_scored_by_penalty_kick.get();
        }

        Integer getPenaltyCatched() {
            return penalty_catched.get();
        }

        Integer getGoalsScoredGoalkeeper() {
            return goals_scored_goalkeeper.get();
        }

        Integer getYellowCards() {
            return yellow_cards.get();
        }

        Integer getRedCards() {
            return red_cards.get();
        }

        Integer getGoalsAgainst() {
            return goals_against.get();
        }

        void setNormalGoalsScored(Integer normal_goals_scored) {
            this.normal_goals_scored.set(normal_goals_scored);
        }

        void setGoalsScoredByPenaltyKick(Integer goals_scored_by_penalty_kick) {
            this.goals_scored_by_penalty_kick.set(goals_scored_by_penalty_kick);
        }

        void setPenaltyCatched(Integer penalty_catched) {
            this.penalty_catched.set(penalty_catched);
        }

        void setGoalsScoredGoalkeeper(Integer goals_scored_goalkeeper) {
            this.goals_scored_goalkeeper.set(goals_scored_goalkeeper);
        }

        void setYellowCards(Integer yellow_cards) {
            this.yellow_cards.set(yellow_cards);
        }

        void setRedCards(Integer red_cards) {
            this.red_cards.set(red_cards);
        }

        void setGoalsAgainst(Integer goals_against) {
            this.goals_against.set(goals_against);
        }
    }

    private class IntegerConverter extends IntegerStringConverter{
        @Override
        public Integer fromString(String value) {
            try {
                return super.fromString(value);
            } catch(NumberFormatException e) {
                Alert alert = createAlert("Solo números permitidos");
                alert.showAndWait();
                return 0;
            }
        }
    }
}
