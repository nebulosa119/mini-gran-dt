package Controllers;

import Models.AccountsManager;
import Models.Tournament;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class TransitionViewController {

    public Scene createMainWindow(){
        Button createTournamentButton = new Button("Create Tournament");
        createTournamentButton.setMaxWidth(Double.MAX_VALUE);
        createTournamentButton.setOnAction(event -> createTextInputDialog("New Tournament", "Input the tournament information","Tournament name","Players per team"));

        Button loadDataButton = new Button("Load Data To Tournaments");
        loadDataButton.setMaxWidth(Double.MAX_VALUE);
        loadDataButton.setOnAction(event -> MainApp.setScene("manageTournaments"));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(createTournamentButton,loadDataButton);
        return new Scene(hBox,600,50);
    }

    public static void show(Dialog textInputDialog) {
        Optional<Tournament> result = textInputDialog.showAndWait();
        //result.ifPresent(AccountsManager::createNewTournament);
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
        dialog.show();
        return dialog;
    }
}
