package Controllers;


import javafx.scene.Scene;

public abstract class ViewController {

    MainApp controller;

    public ViewController(MainApp controller) {
        this.controller = controller;
    }

    //public abstract Scene createMainWindow();

}
