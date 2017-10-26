package src.Model;

public abstract class Identifiable {
    protected String name;
    protected int id;

    public Identifiable(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return new String(name);
    }

    public int getId() {
        return new Integer(id);
    }

    // son iguales si tienen el mismo id y son instancia de la misma clase
    // mismo name no importa(por ahora)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Identifiable))
            return false;

        Identifiable identifiable = (Identifiable) o;
            return this.id == identifiable.id;
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + id;
        return result;
    }
}
