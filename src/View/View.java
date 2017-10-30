package View;


import Controller.Controller;
import Model.Account;
import javafx.scene.Scene;

public abstract class View {
    private Controller controller;
    public View(Controller controller) {
        this.controller = controller;
    }
    public abstract Scene createScene();
}
