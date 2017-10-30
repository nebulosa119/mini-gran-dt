package Controller;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RankingsWindow extends VBox {
    private final String rules = "Da ranks";
    private Label label = new Label();

    public RulesWindow() {
        getChildren().addAll(label);
    }

}
