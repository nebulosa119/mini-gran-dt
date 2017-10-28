package Controller;

import Model.Account;
import Model.User;

import java.util.Vector;

public class AccountsMananger <T extends Account>{
    private Vector<T> accounts;

    public void loadUsers() {
        accounts = (Vector<T>)FileMananger.unserializeObject(Types.USER.fileName);
    }

    public void loadAdmins() {
        accounts = (Vector<T>)FileMananger.unserializeObject(Types.ADMIN.fileName);
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

    //pisa lo que haya en los archivos
    public void save(){
        if (!(accounts == null || accounts.isEmpty())){
            String fileName;
            if (accounts.get(0) instanceof User){
                fileName = Types.USER.fileName;
            }else {
                fileName = Types.ADMIN.fileName;
            }
            System.out.println("guardando en "+fileName);
            FileMananger.serializeObject(accounts,fileName);
        }
    }
}
