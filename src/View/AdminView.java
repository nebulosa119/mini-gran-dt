package View;

import Model.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AdminView extends View {
    private Administrator admin;
    public AdminView(MainApp controller, Administrator admin) {
        super(controller);
        this.admin = admin;
    }

    @Override
    public Scene createMainWindow(){
        Button createTournamentButton = new Button("Create Tournament");
        createTournamentButton.setMaxWidth(Double.MAX_VALUE);
        createTournamentButton.setOnAction(event -> controller.show(createTextInputDialog("New Tournament", "Input the tournament information","Tournament name","Players per team")));

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
        Accordion tAccordion = new Accordion();

        for (Tournament tournament: tournaments) {
            Accordion teamAccordion = new Accordion();

            for (Team team : tournament.getTeams()) {
                TableView table = createPlayersTable(team);
                teamAccordion.getPanes().add(new TitledPane(team.getName(),table));
            }
            tAccordion.getPanes().add(new TitledPane(tournament.getName(),teamAccordion));
        }
        return tAccordion;
    }

    private TableView createPlayersTable(Team team){
        // genera cambio?
        ObservableList<Player> data = FXCollections.observableArrayList(team.getPlayers());

        ArrayList<TableColumn> columnsList = new ArrayList<>();
        //primera columna
        TableColumn PlayerNameCol = new TableColumn();
        PlayerNameCol.setText("Player");
        PlayerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        //PlayerNameCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        columnsList.add(PlayerNameCol);
        // una columna por propiedad
        for (PropValues prop : PropValues.values()) {
            String colName = cleanString(prop.toString());
            columnsList.add(createPropColumn(colName, prop.toString()));
        }

        TableView tableView = new TableView();
        tableView.setItems(data);
        tableView.setEditable(true);
        tableView.getColumns().addAll(columnsList);
        return tableView;

    }

    private TableColumn createPropColumn(String colName, String propertie) {
        TableColumn column = new TableColumn();
        column.setText(colName);
        column.setMinWidth(70);
        column.setCellValueFactory(new PropertyValueFactory(propertie));
        StringConverter<Object> sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                return t == null ? "0" : t.toString();
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        };
        column.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        return column;
    }

    private void uploadData(Accordion tAccordion) {
        Set<Tournament> tournaments = new HashSet<>();
        // para cada acordion de torneo...
        for (TitledPane tourPane : tAccordion.getPanes()) {
            String tourName = tourPane.getText();
            Accordion teamsAccordion = (Accordion)tourPane.getContent();
            tournaments.add(getTournamentData(tourName, teamsAccordion));
        }
        controller.refresh(tournaments);
        System.out.println();
    }

    private Tournament getTournamentData(String tourName, Accordion teamsAccordion) {
        Tournament tournament = new Tournament(tourName);
        // para cada panel de euipo dentro del acordion del torneo...
        for (TitledPane teamPane : teamsAccordion.getPanes()) {
            String teamName = teamPane.getText();
            TableView teamTable = (TableView)teamPane.getContent();
            tournament.addTeam(getTeamData(teamName,teamTable));
        }
        return tournament;
    }

    private Team getTeamData(String teamName, TableView teamTable) {
        Team team = new Team(teamName);
        ObservableList items = teamTable.getItems();
        int tourMaxPlayers = items.size();
        for (Object item : items) {
            team.add((Player)item,tourMaxPlayers);
        }
        return team;
    }

    private String cleanString(String string) {
        string = string.replace('_',' ');// sacamos los _
        string = string.substring(0, 1).toUpperCase() + string.substring(1);// Pasamos a mayuscula la primera;
        return string;
    }

    private Alert createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(message);
        return alert;
    }

    private class SpinnerCell<S, T> extends TableCell<S, T> {

        private Spinner<Integer> spinner;
        private ObservableValue<T> ov;

        SpinnerCell() {
            this.spinner = new Spinner<Integer>(0, 100, 0);
            setAlignment(Pos.CENTER);
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(null);
                setGraphic(this.spinner);

                if (this.ov instanceof IntegerProperty) {
                    this.spinner.getValueFactory().valueProperty().unbindBidirectional(((IntegerProperty) this.ov).asObject());
                }

                this.ov = getTableColumn().getCellObservableValue(getIndex());

                if (this.ov instanceof IntegerProperty) {
                    this.spinner.getValueFactory().valueProperty().bindBidirectional(((IntegerProperty) this.ov).asObject());
                }
            }
        }

        public ObservableValue<T> getObservableValue() {
            return ov;
        }
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
}
