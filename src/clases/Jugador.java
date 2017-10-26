package clases;

// los jugadores no tendran una posicion fija, pueden variar a lo largo del tornero
// por ende a la hora de subir los datos, todos los jugadores seran capaces de recibir
// cualquier atributo.
public class Jugador extends Identificable {
    private int ranking;
    private int precio;

    public Jugador(String nombre, int dni,Integer ranking, int precio) {
        super(nombre, dni);// dni para diferencair entre nombres
        this.ranking = ranking;
        this.precio = precio;
    }

    public Jugador(String nombre, int dni) {
        this(nombre, dni, 0, 0);
    }

    public Integer getRanking() {
        return new Integer(ranking);
    }

    public int getPrecio() {
        return new Integer(precio);
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Jugador))
            return false;
        Jugador jugador = (Jugador) o;

        return super.equals(jugador);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 3; // para diferenciarlo
        return result;
    }
}
