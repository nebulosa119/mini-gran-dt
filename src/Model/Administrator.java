package Model;

import Model.Account;
import Model.Tournament;

import java.util.List;

public class Administrator extends Account{
    private List<Tournament> tournaments;

    public Administrator(String name, int id) {
        super(name, id);
    }

}
