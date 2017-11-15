package Models;

import Models.Exceptions.InsufficientFundsException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que maneja las expensas de un usuario.
 */
public class UserWallet implements Serializable{

    private static final long serialVersionUID = 1L;

    private Map<Tournament, Integer> funds = new HashMap<>();
    private static final int INITIAL_AMOUNT = 10000;

    /**
     * Método que determina si el usuario tiene suficientes fondos para comprar un jugador según los fondos del torneo en particular.
     * @param t
     * @param p
     * @return
     */
    private boolean hasEnough(Tournament t, Player p) {
        return funds.get(t) >= p.getPrice();
    }

    /**
     * Método que registra nuevos fondos para un torneo en particular.
     * @param t
     */
    void addNewFund(Tournament t) {
        funds.put(t, INITIAL_AMOUNT);
    }

    /**
     * Método que devuelve la cantidad de fondos del usuario disponibles en un torneo en particular.
     * @param t
     * @return
     */
    public int getAvailableFunds(Tournament t) {
        return funds.get(t);
    }

    /**
     * Método para que el usuario venda un jugador que tiene en el UserTeam registrado para un torneo en particular.
     * @param t
     * @param p
     */
    void sell(Tournament t, Player p) {
        Integer aux = funds.get(t);
        aux += p.getPrice();
        funds.put(t, aux);
    }

    /**
     * Método para que el usuario compre un jugador para el UserTeam registrado para un torneo en particular.
     * @param t
     * @param p
     * @throws InsufficientFundsException
     */
    void buy(Tournament t, Player p) throws InsufficientFundsException{
        if(!(hasEnough(t, p))) throw new InsufficientFundsException();
        Integer aux = funds.get(t);
        aux -= p.getPrice();
        funds.put(t, aux);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(funds);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        funds = (Map<Tournament, Integer>) ois.readObject();
    }
}
