package Models;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class UserTeam extends AbstractTeam implements Serializable{

    private static final long serialVersionUID = 1L;

    private int points = 0;

    public UserTeam(int max_players) {
        super(max_players);
    }

    public void refreshPoints(Player.Properties p) {
        points += p.getPoints();
        System.out.println("Points: " + points);
    }

    public int getUserPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof UserTeam))
            return false;
        UserTeam aux = (UserTeam) o;
        return super.equals(aux);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(points);
        out.writeObject(players);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        points = ois.readInt();
        players= (ArrayList<Player>) ois.readObject();
    }
}
