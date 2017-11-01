package controller;

import model.Account;
import model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountsManager implements Serializable{
    private ArrayList<Account> accounts;

    AccountsManager() {
            accounts = new ArrayList<>();
    }

    public void loadAccounts() {
        accounts = (ArrayList<Account>) FileManager.unserializeObject(Types.USER.fileName);// no se como se soluciona
    }

    public boolean contains(String username) {
        System.out.println();
        return getAccount(username) != null;
    }

    public Account getAccount(String username) {
        for (Account aux: accounts) {
            if (aux.getName().equals(username))
                return aux;
        }
        return null;
    }

    //pisa lo que haya en los archivos
    public void save(){
        // (!(accounts == null || accounts.isEmpty())){
         //   String fileName = Types.USER.fileName;
           // System.out.println("guardando en "+fileName);
            //FileManager.serializeObject(accounts,fileName);
       //
        //FileManager.serializeObject(this,Types.USER.fileName);
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
