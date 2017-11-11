package Models;

import Models.Exceptions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;

public class UserExpenses {

    private Map<Tournament, Integer> funds = new HashMap<>();
    private static final int INITIAL_AMOUNT = 100000;

    private boolean hasEnough(Tournament t, Player p) {
        return funds.containsKey(t) && funds.get(t) <= p.getPrice();
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
}
