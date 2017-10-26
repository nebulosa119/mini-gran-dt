package src.Controller;

import src.Model.Account;
import src.Model.User;
import src.Model.exceptions.ExistentNameException;
import src.Model.exceptions.IdAlreadyUsedException;

import java.util.Vector;

public class AccountsMananger<T extends  Account> {
    private Vector<T> accounts;

    public AccountsMananger(Vector<T> accounts) {
        this.accounts = accounts;
    }

    private void add(T c) throws ExistentNameException, IdAlreadyUsedException {
        if (accounts == null)
            accounts = new Vector<>();
        for (Account aux : accounts) {
            if (aux.getName().equals(c.getName())){
                throw new ExistentNameException();
            }
            if (aux.getId() == c.getId()){
                throw new IdAlreadyUsedException();
            }
        }
        accounts.add(c);
    }

    public Account getAccount(String name){
        for (Account aux: accounts) {
            if (aux.getName().equals(name))
                return aux; // no se si retornar una copia o la misma,
        }
        return null;
    }

    public boolean correctInfo(String name, int id){
        if (accounts == null){
            accounts = new Vector<>();
        }else {
            for (Account aux: accounts) {
                if (aux.getName().equals(name) && aux.getId() == id)
                    return true;
            }
        }
        return false;

    }
}
