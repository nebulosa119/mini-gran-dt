package Model;

import java.util.Vector;

public class Properties {
    private int  normal_goals_scored;
    private int goals_scored_by_penalty_kick;
    private int penalty_catched;
    private int goals_scored_goalkeeper;
    private int yellow_cards;
    private int red_cards;
    private int goals_against;

    public void refresh(Properties p){
        this.normal_goals_scored            += p.normal_goals_scored;
        this.goals_scored_by_penalty_kick   += p.goals_scored_by_penalty_kick;
        this.penalty_catched                += p.penalty_catched;
        this.goals_scored_goalkeeper        += p.goals_scored_goalkeeper;
        this.yellow_cards                   += p.yellow_cards;
        this.red_cards                      += p.red_cards;
        this.goals_against                  += p.goals_against;
    }

    public int calculateRanking() {
        int resp=0;
        resp += normal_goals_scored * PropValues.normal_goals_scored.value;
        resp += goals_scored_by_penalty_kick * PropValues.normal_goals_scored.value;
        resp += penalty_catched * PropValues.normal_goals_scored.value;
        resp += goals_scored_goalkeeper * PropValues.normal_goals_scored.value;
        resp += yellow_cards * PropValues.normal_goals_scored.value;
        resp += red_cards * PropValues.normal_goals_scored.value;
        resp += goals_against * PropValues.normal_goals_scored.value;
        return resp;

    }
    private enum PropValues{
        normal_goals_scored(20),
        goals_scored_by_penalty_kick(10),
        penalty_catched(20),
        goals_scored_goalkeeper(60),
        yellow_cards(-5),
        red_cards(-10),
        goals_against(-20);

        int value;

        PropertiesValues(int value) {
            this.value = value;
        }
    }
}
