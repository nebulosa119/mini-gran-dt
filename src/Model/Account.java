package Model;

import java.io.Serializable;
import java.util.Set;

public abstract class Account extends Identifiable implements Serializable {
    public Account() {
        super();
    }

    public Account(String name) {
        super(name);
    }
}
