package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Administrator extends Account {

    private static final long serialVersionUID = 1L;

    private TournamentsAdministration tournamentsAdministration = new TournamentsAdministration(this);

    public Administrator(String name) {
        super(name);
    }

    public TournamentsAdministration getAdministration() {
        return tournamentsAdministration;
    }

    public void refreshUsers(Player.Properties p, Tournament t) {
        tournamentsAdministration.refreshUsers(p, t);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeObject(tournamentsAdministration);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        name = ois.readUTF();
        tournamentsAdministration = (TournamentsAdministration)ois.readObject();
    }
}
