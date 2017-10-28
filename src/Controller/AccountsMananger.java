package Controller;

import Model.Account;

import java.util.Vector;

public class AccountsMananger <T extends Account>{
    private Vector<T> accounts;

    public void loadUsers() {
        accounts = (Vector<T>)FileMananger.readObjectFromFile(Types.USER.fileName);
    }

    public void loadAdmins() {
        accounts = (Vector<T>)FileMananger.readObjectFromFile(Types.ADMIN.fileName);
    }

    public boolean contains(String username) {
        return getAccount(username) != null;
    }

    public T getAccount(String username) {
        for (T aux: accounts) {
            if (aux.getName().equals(username));
            return aux;
        }
        return null;
    }

    public void add(T account) {
        accounts.add(account);
    }
}
