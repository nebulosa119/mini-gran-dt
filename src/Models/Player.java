package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// los jugadores no tendran una posicion fija, pueden variar a lo largo del tornero
// por ende a la hora de subir los datos, todos los jugadores seran capaces de recibir
// cualquier atributo.
public class Player extends Identifiable implements Serializable{

    private static final long serialVersionUID = 1L;

    private int price;
    private Properties properties;

    public Player(String name, Properties properties) {
        super(name);
        this.properties = properties;
    }
    public Player(String name) {
        this(name,new Properties());
    }

    public Player(String name, int price) {
        this(name);
        this.price = price;
    }

    public void refresh(Properties p) {
        properties.refresh(p);
    }

    public int getCalculatedPrice() {
        return properties.calculatePrice();
    }

    public int getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Player))
            return false;
        Player player = (Player) o;

        return super.equals(player);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 3; // para diferenciarlo
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getRanking() {
        return properties.calculateRanking();
    }

    public String getProperties() {
        return properties.toString();
    }

    //falta agregar la clase properties y muevo el serializable

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeInt(price);
        /*
        out.writeInt(normal_goals_scored);
        out.writeInt(goals_scored_by_penalty_kick);
        out.writeInt(penalty_catched);
        out.writeInt(goals_scored_goalkeeper);
        out.writeInt(yellow_cards);
        out.writeInt(red_cards);
        out.writeInt(goals_against);
         */
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        price = ois.readInt();
        /*
        ois.writeInt(normal_goals_scored);
        ois.writeInt(goals_scored_by_penalty_kick);
        ois.writeInt(penalty_catched);
        ois.writeInt(goals_scored_goalkeeper);
        ois.writeInt(yellow_cards);
        ois.writeInt(red_cards);
        ois.writeInt(goals_against);
         */
    }
}
