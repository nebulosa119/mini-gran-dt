package clases;

public abstract class Identificable {
    protected String nombre;
    protected int id;

    public Identificable(String nombre, int id) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return new String(nombre);
    }

    public int getId() {
        return new Integer(id);
    }

    // son iguales si tienen el mismo id y son instancia de la misma clase
    // mismo nombre no importa(por ahora)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Identificable))
            return false;

        Identificable identificable = (Identificable) o;
            return this.id == identificable.id;
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + id;
        return result;
    }
}
