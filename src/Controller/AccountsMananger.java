package Controller;

import Model.Account;
import Model.Administrator;
import Model.User;

import java.util.Vector;

public class AccountsMananger {
    private Vector<Account> accounts;

    public void loadAccounts() {
        accounts = (Vector<Account>)FileMananger.unserializeObject(Types.USER.fileName);
    }

    public boolean contains(String username) {
        return getAccount(username) != null;
    }

    public Account getAccount(String username) {
        for (Account aux: accounts) {
            if (aux.getName().equals(username));
            return aux;
        }
        return null;
    }

    //pisa lo que haya en los archivos
    public void save(){
        if (!(accounts == null || accounts.isEmpty())){
            String fileName = Types.USER.fileName;
            System.out.println("guardando en "+fileName);
            FileMananger.serializeObject(accounts,fileName);
        }
    }

    public void createAccount(String username, Types type) {
        if (Types.USER.equals(type)){
            accounts.add(new User(username));
        }else {
            accounts.add(new Administrator(username));
        }
    }
    public void createAccount(Account account) {
        accounts.add(account);
    }


}
