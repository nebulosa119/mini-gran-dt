package View;

import Controller.MainApp;
import Model.PropValues;
import Model.Team;
import Model.Tournament;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class AdminView extends View {
    public AdminView(MainApp controller) {
        super(controller);
    }

    @Override
    public Scene createMainWindow(){
        Pane pane = new Pane();
        HBox hBox = new HBox();
        Button createTournamentButton = new Button("Create Tournament");
        createTournamentButton.setMaxWidth(Double.MAX_VALUE);
        createTournamentButton.setOnAction(event -> controller.show(createTextInputDialog("New Tournament", "Input the tournament information","Tournamnet name","Players per team")));
        Button loadDatabutton = new Button("Load Data To Tournaments");
        loadDatabutton.setMaxWidth(Double.MAX_VALUE);
        loadDatabutton.setOnAction(event -> controller.setScene(createLoadDataScene()));
        hBox.getChildren().addAll(createTournamentButton,loadDatabutton);
        pane.getChildren().add(hBox);
        return new Scene(pane,600,50);
    }

    private Scene createLoadDataScene(){
        Accordion tAccordion = createTournamentsView(controller.getAccountTournaments());
        tAccordion.setMinSize(100, 100);

        Button confrimButton = new Button("Confrim");
        confrimButton.setMaxWidth(Double.MAX_VALUE);
        confrimButton.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                //alert.getDialogPane().setContentText(Alert.AlertType.CONFIRMATION + " text.");
                alert.getDialogPane().setHeaderText("Are you shure?");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> controller.setScene(createMainWindow()));
        });
        Button backButton = new Button("Back");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setOnAction(event -> controller.setScene(createMainWindow()));

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(backButton,confrimButton);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tAccordion,buttonBox);
        return new Scene(vBox,600,600);
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

    private Accordion createTournamentsView(ArrayList<Tournament> tournaments){
        Accordion tourAccoridion = new Accordion();

        for (Tournament tournament: tournaments) {
            Accordion teamAccordion = new Accordion();

            for (Team team:tournament.getTeams()) {
                TableView table = createPlayersTable(team);
                teamAccordion.getPanes().add(new TitledPane(team.getName(),table));
            }
            tourAccoridion.getPanes().add(new TitledPane(tournament.getName(),teamAccordion));
        }
        return tourAccoridion;
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
