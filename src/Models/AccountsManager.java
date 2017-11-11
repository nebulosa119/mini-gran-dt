package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Clase que maneja todas las cuentas de usuarios del programa Mini Gran DT.
 *
 * @author
 */
public class AccountsManager implements Serializable{

    private static final long serialVersionUID = 1L;

    private static ArrayList<Account> accounts;
    private static Account account;
    private static AccountsManager instance;

    private AccountsManager(){
        instance = this;
        accounts = new ArrayList<>();
    }

    public static AccountsManager getInstance(){
        if(instance == null)
            instance = new AccountsManager();
        return instance;
    }

    public Account getSignedAccount() {
        return account;
    }

    public ArrayList<Administrator> getAdmins() {
        ArrayList<Administrator> aux = new ArrayList<>();
        for(Account a : accounts) {
            if(a instanceof Administrator)
                aux.add((Administrator)a);
        }
        return aux;
    }

    public static boolean contains(String username) {
        if(username.equals(""))
            return false;
        return getAccount(username) != null;
    }

    public static boolean createAdmin(String username) {
        if(username.equals(""))
            return false;
        accounts.add(new Administrator(username));
        return true;
    }

    public static boolean createUser(String username) {
        if(username.equals(""))
            return false;
        accounts.add(new User(username));
        return true;
    }

    public static Account getAccount(String username) {
        for (Account aux: accounts) {
            if (aux.getName().equals(username))
                return aux;
        }
        return null;
    }

    public static void setAccount(String accountName) {
        account = getAccount(accountName);
    }

    public static boolean accountIsUser() {
        if (account != null)
            return account instanceof  User;
        return false;
    }

    public static void refresh(Map<String,Map<String,Map<String, Player.Properties>>> tournaments) {
        if (account instanceof Administrator)
            ((Administrator) account).refresh(tournaments);
    }

    public static Set<Tournament> getTournaments() {
        return ((Administrator)account).getTournaments();
    }

    /** Carga los usuarios existentes al programa */
    public static void loadAccounts() {
        accounts = (ArrayList<Account>) FileManager.readFromFile("accountsData.temp");
    }
    /** Guarda los usuarios existentes */
    public static void save(){
        FileManager.writeToFile(accounts,"accountsData.temp");
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(accounts);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        accounts = (ArrayList)ois.readObject();
    }
}
