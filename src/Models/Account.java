package Models;

import java.io.Serializable;

/**
 * Clase que representa a un usuario del programa Mini Gran DT.
 *
 * @author
 */
public abstract class Account implements Serializable {

    String name;

    /**
     * Crea una nueva cuenta.
     *
     * @param name Nombre del usuario
     */
    public Account(String name) {
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

        Account account = (Account) o;

        return name != null ? name.equals(account.name) : account.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
