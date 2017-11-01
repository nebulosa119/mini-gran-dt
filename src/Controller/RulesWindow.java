package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RulesWindow extends VBox {

    private final String rules = "Da rules";
    private Label label = new Label();

    public RulesWindow() {
        getChildren().addAll(label);
    }

}
