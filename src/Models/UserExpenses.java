package Models;

import Models.Exceptions.InsufficientFundsException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserExpenses implements Serializable{

    private static final long serialVersionUID = 1L;

    private Map<Tournament, Integer> funds = new HashMap<>();
    private static final int INITIAL_AMOUNT = 100000;

    private boolean hasEnough(Tournament t, Player p) {
        System.out.println(p.getPrice());
        return funds.get(t) >= p.getPrice();
    }

    void addNewFund(Tournament t) {
        funds.put(t, INITIAL_AMOUNT);
    }

    void sell(Tournament t, Player p) {
        Integer aux = funds.get(t);
        aux += p.getPrice();
        funds.remove(t);
        funds.put(t, aux);
    }

    void buy(Tournament t, Player p) throws InsufficientFundsException{
        if(!(hasEnough(t, p))) throw new InsufficientFundsException();
        Integer aux = funds.get(t);
        aux -= p.getPrice();
        funds.remove(t);
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
