package Models;

public enum PropValues{
    normal_goals_scored(20,40,0.35),
    goals_scored_by_penalty_kick(10,20,0.1),
    penalty_catched(20,40,0.2),
    goals_scored_goalkeeper(60,120,0.35),
    yellow_cards(-5,-10,0),
    red_cards(-10,-20,0),
    goals_against(-20,-40,0);

    int pValue;
    int uValue;
    double uPricePerCent;

    PropValues(int pValue,int uValue,double uPricePerCent) {
        this.pValue = pValue;
        this.uValue = uValue;
        this.uPricePerCent = uPricePerCent;
    }

    public int getpValue() {
        return pValue;
    }

    public int getuValue() {
        return uValue;
    }

    public double getUPricePerCent() {
        return uPricePerCent;
    }
}