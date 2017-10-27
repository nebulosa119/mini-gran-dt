package Controller;

import Model.Account;
import Model.Administrator;
import Model.User;
import Model.exceptions.ExistentNameException;
import Model.exceptions.IdAlreadyUsedException;

import java.util.Vector;

public class AccountsMananger<T extends  Account> {
    private Vector<T> accounts;

    public AccountsMananger(Vector<T> accounts) {
        this.accounts = accounts;
    }

    public void add(T c) {
        if (accounts == null)
            accounts = new Vector<>();
        accounts.add(c);
    }

    private Account getAccount(String name){
        for (Account aux: accounts) {
            if (aux.getName().equals(name))
                return aux; // no se si retornar una copia o la misma,
        }
        return null;
    }

    public User getUser(String name){
        return (User) getAccount(name); // no se si retornar una copia o la misma,
    }
    public Administrator getAdmin(String name){
        return (Administrator) getAccount(name); // no se si retornar una copia o la misma,
    }

    public boolean correctInfo(String name){
        if (accounts == null){
            accounts = new Vector<>();
        }else {
            for (Account aux: accounts) {
                if (aux.getName().equals(name))
                    return true;
            }
        }
        return false;

    }
}
