package Models;

import java.io.Serializable;

/**
 * Clase que representa a un usuario del programa Mini Gran DT.
 *
 * @author
 */
public abstract class Account extends Identifiable implements Serializable {

    public Account() {
        super();
    }

    /**
     * Crea una nueva cuenta.
     *
     * @param name Nombre del usuario
     */
    public Account(String name) {
        super(name);
    }
}
