package view;


import controller.MainApp;
import javafx.scene.Scene;

public abstract class View {
    MainApp controller;
    View(MainApp controller) {
        this.controller = controller;
    }

    public abstract Scene createMainWindow();
}
