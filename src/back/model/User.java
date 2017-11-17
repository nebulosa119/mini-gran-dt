package back.model;

import java.io.Serializable;

/**
 * Clase que representa a un usuario del programa Mini Gran DT.
 *
 */
public abstract class User implements Serializable {

    String name;

    /**
     * Crea una nueva cuenta.
     *
     * @param name Nombre del usuario
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Retorna el nombre de la cuenta
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
