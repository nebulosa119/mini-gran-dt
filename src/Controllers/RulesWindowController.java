package Controllers;

import Models.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de las reglas en un torneo.
 *
 * @author tdorado
 */
public class RulesWindowController implements Initializable {
    @FXML
    private Label scoredGoal, penaltyGoal, goaleeGoal, penaltyCatched, redCard, yellowCard, ownGoal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scoredGoal.setText("Gol anotado suma " + Player.Properties.PropValues.normal_goals_scored.getpValue() + " puntos.");
        penaltyGoal.setText("Gol anotado por penal suma " + Player.Properties.PropValues.goals_scored_by_penalty_kick.getpValue() + " puntos.");
        goaleeGoal.setText("Gol de arquero suma " + Player.Properties.PropValues.goals_scored_goalkeeper.getpValue() + " puntos.");
        penaltyCatched.setText("Penal atajado suma " + Player.Properties.PropValues.penalty_catched.getpValue() + " puntos.");
        redCard.setText("Tarjeta roja resta " + Player.Properties.PropValues.red_cards.getpValue() + " puntos.");
        yellowCard.setText("Tarjeta amarilla resta " + Player.Properties.PropValues.yellow_cards.getpValue() + " puntos.");
        ownGoal.setText("Gol en contra resta " + Player.Properties.PropValues.goals_against.getpValue() + " puntos.");
    }
}
