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
}
