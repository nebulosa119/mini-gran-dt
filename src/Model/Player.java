package model;

// los jugadores no tendran una posicion fija, pueden variar a lo largo del tornero
// por ende a la hora de subir los datos, todos los jugadores seran capaces de recibir
// cualquier atributo.
public class Player extends Identifiable {

    private int price;
    private Properties properties;

    public Player(String name) {
        super(name);
        properties = new Properties();
    }

    public void refresh(Properties p) {
        properties.refresh(p);
    }

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
}
