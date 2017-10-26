package Model;

public class Player {
    private Position position;
    private String name;
    private int ranking;


    private enum Position {
        forward, goalKeeper, defender, midfield;
    }
}
