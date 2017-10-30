package Controller;

import Model.Team;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;

import java.util.Map;

public class UserManager {
    private Map<String,Team> teams;

    public UserManager(Map<String, Team> teams) {
        this.teams = teams;
    }

    public Parent createVbox() {
        VBox vBox = new VBox();
        vBox.getChildren().add(new ButtonBar());

        return vBox;
    }
}
