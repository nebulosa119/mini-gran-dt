package Model;

// los jugadores no tendran una posicion fija, pueden variar a lo largo del tornero
// por ende a la hora de subir los datos, todos los jugadores seran capaces de recibir
// cualquier atributo.
public class Player extends Identifiable {

    private int ranking;
    private int price;

    public Player(String name, Integer ranking, int price) {
        super(name);// dni para diferencair entre names
        this.ranking = ranking;
        this.price = price;
    }

    public Player(String name, int dni) {
        this(name, 0, 0);
    }

    public Integer getRanking() {
        return new Integer(ranking);
    }

    public int getPrice() {
        return new Integer(price);
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public void setPrice(int price) {
        this.price = price;
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
}
