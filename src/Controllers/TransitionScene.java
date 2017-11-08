package Controllers;

import Models.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class TransitionScene implements Initializable{
    @FXML
    private Button addPlayerPropertiesButton, editTournButton;

    private Administrator administrator;
    private Player player;

    public void initialize(Administrator administrator, Player player) {
        this.administrator = administrator;
        this.player = player;

        addPlayerPropertiesButton.setOnAction(addPlayerStatsHandler);
        editTournButton.setOnAction(editTournHandler);
    }


    private EventHandler addPlayerStatsHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            //PILO esto abre la ventana para poner lo que hizo cada jugador en el partido

        }
    };


    private EventHandler editTournHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
        //aca se  abre la ventana para crear y editar torneos
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

