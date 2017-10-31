package View;


import Controller.Controller;
import Model.Account;
import javafx.scene.Scene;

public abstract class View {
    Controller controller;
    View(Controller controller) {
        this.controller = controller;
    }
    public abstract Scene createScene();
}
