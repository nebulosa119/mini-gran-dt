package Controller;

import Model.Account;
import Model.Administrator;
import Model.User;

import java.util.Vector;

public class AccountsManager {
    private Vector<Account> accounts;

    public AccountsManager() {
        loadAccounts();
        if(accounts == null){
            accounts = new Vector<>();
        }

    }

    public void loadAccounts() {
        accounts = (Vector<Account>) FileManager.unserializeObject(Types.USER.fileName);// no se como se soluciona
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
            FileManager.serializeObject(accounts,fileName);
        }
    }

    public void createAccount(String username) {
            accounts.add(new User(username));
    }

    public void createAccount(Account account) {
        accounts.add(account);
    }


}
