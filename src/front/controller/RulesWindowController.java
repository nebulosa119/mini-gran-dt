package front.controller;

import back.model.PhysicalPlayer;
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

        scoredGoal.setText("Gol anotado suma " + PhysicalPlayer.Properties.PropValues.normal_goals_scored.getRankingValue() + " puntos.");
        penaltyGoal.setText("Gol anotado por penal suma " + PhysicalPlayer.Properties.PropValues.goals_scored_by_penalty_kick.getRankingValue() + " puntos.");
        goaleeGoal.setText("Gol de arquero suma " + PhysicalPlayer.Properties.PropValues.goals_scored_goalkeeper.getRankingValue() + " puntos.");
        penaltyCatched.setText("Penal atajado suma " + PhysicalPlayer.Properties.PropValues.penalty_catched.getRankingValue() + " puntos.");
        redCard.setText("Tarjeta roja resta " + PhysicalPlayer.Properties.PropValues.red_cards.getRankingValue() + " puntos.");
        yellowCard.setText("Tarjeta amarilla resta " + PhysicalPlayer.Properties.PropValues.yellow_cards.getRankingValue() + " puntos.");
        ownGoal.setText("Gol en contra resta " + PhysicalPlayer.Properties.PropValues.goals_against.getRankingValue() + " puntos.");

    }
}
