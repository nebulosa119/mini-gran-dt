package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Account extends Identifiable implements Serializable {
    public Account() {
        super();
    }

    public Account(String name) {
        super(name);
    }

    public boolean isUser() {
        return this instanceof User;
    }
    public boolean isAdmin() {
        return this instanceof Administrator;
    }

    public abstract ArrayList<String> getTournamentNames();
}
