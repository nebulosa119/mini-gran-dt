package View;


import Controller.Controller;
import javafx.scene.Scene;

public abstract class View {
    Controller controller;
    View(Controller controller) {
        this.controller = controller;
    }

    public abstract Scene createMainWindow();
}
