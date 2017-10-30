package Controller;

import Model.Team;
import Model.User;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;

import java.util.Map;

public class TeamController {//  crea la ventana del user

    @FXML
    private Button exitButton, ruleButton, playerRankingButton, addPlayerButton, removePlayerButton;
    private User u;

    public void initModel(User u) {
        this.u = u;
        exitButton.setOnAction(exitHandler);
        ruleButton.setOnAction(ruleHandler);
        playerRankingButton.setOnAction(rankingHandler);
        addPlayerButton.setOnAction(addPlayerHandler);
        removePlayerButton.setOnAction(removePlayerHandler);
    }

    private EventHandler exitHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Guarda y sale*/

        }
    };

    private EventHandler ruleHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Abre la ventana de reglas*/
        }
    };

    private EventHandler rankingHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Abre la ventana de posicion de jugaodores*/
        }
    };

    private EventHandler addPlayerHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Añade el jugador elegido y decrementa los fondos*/
        }
    };

    private EventHandler removePlayerHandler = new EventHandler(){

        @Override
        public void handle(Event event) {
            /**Remueve el jugador elegido y aumenta los fondos*/
        }
    };

}
