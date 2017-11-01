package Model;

import java.util.Comparator;

public abstract class Identifiable {

    protected String name;

    public Identifiable() { // ver que onda 
        //this.name = "";
    }

    public Identifiable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // son iguales si tienen el mismo nombre y son instancia de la misma clase
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Identifiable))
            return false;

        Identifiable identifiable = (Identifiable) o;
            return this.name.equals(identifiable.name);
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + name.hashCode();
        return result;
    }

}
