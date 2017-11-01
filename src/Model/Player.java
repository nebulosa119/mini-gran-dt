package Model;

// los jugadores no tendran una posicion fija, pueden variar a lo largo del tornero
// por ende a la hora de subir los datos, todos los jugadores seran capaces de recibir
// cualquier atributo.
public class Player extends Identifiable {

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

    public int getPrice() {
        return properties.calculatePrice();
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

    public void refresh(Player dataPlayer) {
        properties.refresh(dataPlayer.properties);
        System.out.println(getName()+" "+getProperties());
    }

    public String getProperties() {
        return properties.toString();
    }
}
