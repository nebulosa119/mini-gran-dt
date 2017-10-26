package src.Controller;

import src.Model.Account;
import src.Model.FileMananger;
import src.Model.User;
import src.Model.exceptions.ExistentNameException;
import src.Model.exceptions.IdAlreadyUsedException;

import java.util.Vector;

public class AccountsMananger {
    private static Vector<Account> accounts = (Vector<Account>) FileMananger.readObjectFromFile("accounts.temp");

    public static void addUser(User u) throws ExistentNameException, IdAlreadyUsedException {
        add(u);
    }

    private static void add(Account c) throws ExistentNameException, IdAlreadyUsedException {
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

    public static Account getAccount(String name){
        for (Account aux: accounts) {
            if (aux.getName().equals(name))
                return aux; // no se si retornar una copia o la misma,
        }
        return null;
    }

    public static boolean correctInfo(String name, String passwd){
        if (accounts == null){
            accounts = new Vector<>();
        }else {
            for (Account aux: accounts) {
                if (aux.getName().equals(name) && aux.getPasswd().equals(passwd))
                    return true;
            }
        }
        return false;

    }
}
