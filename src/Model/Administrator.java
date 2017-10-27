package Model;


import src.Model.Account;
import src.Model.Tournament;

import java.util.List;

public class Administrator extends Account{
    private List<Tournament> tournaments;

    public Administrator(String name, int id) {
        super(name, id);
    }

}
