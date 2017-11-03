package Model;

public class Properties {

    private int normal_goals_scored;
    private int goals_scored_by_penalty_kick;
    private int penalty_catched;
    private int goals_scored_goalkeeper;
    private int yellow_cards;
    private int red_cards;
    private int goals_against;

    public Properties() {
        this.normal_goals_scored = 0;
        this.goals_scored_by_penalty_kick = 0;
        this.penalty_catched = 0;
        this.goals_scored_goalkeeper = 0;
        this.yellow_cards = 0;
        this.red_cards = 0;
        this.goals_against = 0;
    }

    public Properties(int normal_goals_scored, int goals_scored_by_penalty_kick, int penalty_catched, int goals_scored_goalkeeper, int yellow_cards, int red_cards, int goals_against) {
        this.normal_goals_scored = normal_goals_scored;
        this.goals_scored_by_penalty_kick = goals_scored_by_penalty_kick;
        this.penalty_catched = penalty_catched;
        this.goals_scored_goalkeeper = goals_scored_goalkeeper;
        this.yellow_cards = yellow_cards;
        this.red_cards = red_cards;
        this.goals_against = goals_against;
    }

    public void setProperty(int index, int property) {
        switch (index) {
            case 0: normal_goals_scored = property;
                    break;
            case 1: goals_scored_by_penalty_kick = property;
                    break;
            case 2: penalty_catched = property;
                    break;
            case 3: goals_scored_goalkeeper = property;
                    break;
            case 4: yellow_cards = property;
                    break;
            case 5: red_cards = property;
                    break;
            case 6: goals_against = property;
                    break;
        }
    }

    public void refresh(Properties p) {
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
        resp += normal_goals_scored             * PropValues.normal_goals_scored.getpValue();
        resp += goals_scored_by_penalty_kick    * PropValues.goals_scored_by_penalty_kick.getpValue();
        resp += penalty_catched                 * PropValues.penalty_catched.getpValue();
        resp += goals_scored_goalkeeper         * PropValues.goals_scored_goalkeeper.getpValue();
        resp -= yellow_cards                    * PropValues.yellow_cards.getpValue();
        resp -= red_cards                       * PropValues.red_cards.getpValue();
        resp -= goals_against                   * PropValues.goals_against.getpValue();
        return resp;
    }

    public int calculatePrice() {
        int resp = 0;
        resp += normal_goals_scored         * 100 * PropValues.normal_goals_scored.getUPricePerCent();
        resp += goals_scored_by_penalty_kick* 100 * PropValues.goals_scored_by_penalty_kick.getUPricePerCent();
        resp += penalty_catched             * 100 * PropValues.penalty_catched.getUPricePerCent();
        resp += goals_scored_goalkeeper     * 100 * PropValues.goals_scored_goalkeeper.getUPricePerCent();
        return resp;
    }

    public int getPoints() {
        return normal_goals_scored+goals_scored_by_penalty_kick+penalty_catched+goals_scored_goalkeeper-yellow_cards-red_cards-goals_against;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "normal_goals_scored=" + normal_goals_scored +
                ", goals_scored_by_penalty_kick=" + goals_scored_by_penalty_kick +
                ", penalty_catched=" + penalty_catched +
                ", goals_scored_goalkeeper=" + goals_scored_goalkeeper +
                ", yellow_cards=" + yellow_cards +
                ", red_cards=" + red_cards +
                ", goals_against=" + goals_against +
                '}';
    }
}
