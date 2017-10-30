package Controller;

import Model.Account;
import Model.Administrator;
import Model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayList;

public class AccountsManager implements Serializable{
    private ArrayList<Account> accounts;

    public AccountsManager() {
        //loadAccounts();
        //if(accounts == null){
            accounts = new ArrayList<>();
        //}
    }

    public void loadAccounts() {
        accounts = (ArrayList<Account>) FileManager.unserializeObject(Types.USER.fileName);// no se como se soluciona
    }

    public boolean contains(String username) {
        return getAccount(username) != null;
    }

    public Account getAccount(String username) {
        System.out.println("naja");
        for (Account aux: accounts) {
            System.out.println("aca");
            if (aux.getName().equals(username));
            return aux;
        }
        System.out.println("jassadfjkl");
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


    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
