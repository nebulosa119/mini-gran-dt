package Model;

import java.util.Vector;

public class Administrator extends Account{
    private Vector<Tournament> tournaments;

    public Administrator(String name) {
        super(name);
        this.tournaments = new Vector<>();
    }

}
