package Models;

import Models.Exceptions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;

public class UserExpenses {

    private Map<Tournament, Integer> funds = new HashMap<>();
    private User u;
    private static final int INITIAL_AMOUNT = 100000;

    public UserExpenses(User u) {
        this.u = u;
    }

    public boolean hasEnough(Tournament t, Player p) {
        if(funds.containsKey(t)) return funds.get(t) <= p.getPrice();
        return false;
    }

    public void addNewFund(Tournament t) {
        funds.put(t, INITIAL_AMOUNT);
    }

    public void sell(Tournament t, Player p) {
        Integer aux = funds.get(t);
        aux += p.getPrice();
        funds.remove(t);
        funds.put(t, aux);
    }

    public void buy(Tournament t, Player p) throws InsufficientFundsException{
        if(!(hasEnough(t, p))) throw new InsufficientFundsException();
        Integer aux = funds.get(t);
        aux -= p.getPrice();
    }
}
