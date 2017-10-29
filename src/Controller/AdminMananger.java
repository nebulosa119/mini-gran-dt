package Controller;

import Model.PropValues;
import Model.Team;
import Model.Tournament;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.ArrayList;

/**
 * A simple table that uses cell factories to enable editing of boolean and
 * String values in the table.
 */
public class AdminMananger {

    private ArrayList<Tournament> tournaments;

    public AdminMananger(ArrayList<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public void setTournaments(ArrayList<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    private TableView createPlayersTable(Team team){
        ObservableList data = FXCollections.observableArrayList(team.getPlayers());

        StringConverter<Object> sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                return t == null ? null : t.toString();
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        };// para manejar cambios de nombre


        ArrayList<TableColumn> columnsList = new ArrayList<TableColumn>();

        TableColumn PlayerNameCol = new TableColumn();
        PlayerNameCol.setText("Player");
        PlayerNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        PlayerNameCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        columnsList.add(PlayerNameCol);

        for (PropValues prop: PropValues.values()) {
            String colName = prop.toString();
            colName = colName.replace('_',' ');// sacamos los _
            colName = colName.substring(0, 1).toUpperCase() + colName.substring(1);// Pasamos a mayuscula la primera;

            TableColumn column = new TableColumn<>();
            column.setText(colName);
            column.setMinWidth(70);
            column.setCellValueFactory(new PropertyValueFactory(prop.toString()));
            column.setCellFactory(col -> new SpinnerCell());

            columnsList.add(column);
        }

        TableView tableView = new TableView();
        tableView.setItems(data);
        tableView.setEditable(true);
        tableView.getColumns().addAll(columnsList);
        return tableView;

    }
    public Accordion createTournamentsView(ArrayList<Tournament> tournaments){
        Accordion tourAccoridion = new Accordion();

        for (Tournament tournament: tournaments) {
            Accordion teamAccoridion = new Accordion();

            for (Team team:tournament.getTeams()) {
                TableView table = createPlayersTable(team);
                teamAccoridion.getPanes().add(new TitledPane(team.getName(),table));
            }
            tourAccoridion.getPanes().add(new TitledPane(tournament.getName(),teamAccoridion));
        }
        return tourAccoridion;
    }

    public VBox createVBox() {

        Accordion tAccordion = createTournamentsView(tournaments);
        tAccordion.setMinSize(100, 100);

        VBox vBox = new VBox();
        vBox.getChildren().add(tAccordion);

        return vBox;
    }

    public ArrayList<Tournament> getTournaments() {
        return tournaments;
    }

    private class ConfirmEventHandler<T extends Event> implements EventHandler {

        @Override
        public void handle(Event event) {
            System.out.println("hola");
        }
    }

    private class SpinnerCell<S, T> extends TableCell<S, T> {

        private Spinner<Integer> spinner;
        private ObservableValue<T> ov;

        public SpinnerCell() {
            this.spinner = new Spinner<Integer>(0, 100, 1);
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
    }

}