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

    /**
     * Retrona el usuario que está logeado
     */
    public static Account getSignedAccount() {
        return account;
    }
    /**
     * Retrona una lista con todos aquellos que sean administradores
     */
    public static ArrayList<Administrator> getAdmins() {
        ArrayList<Administrator> aux = new ArrayList<>();
        for(Account a : accounts) {
            if(a instanceof Administrator)
                aux.add((Administrator)a);
        }
        return aux;
    }
    /**
     * Retrona los torneos del administrador que está logeado
     */
    public static Set<Tournament> getTournaments() {
        return ((Administrator)account).getTournaments();
    }
    /**
     * Crea un administrador
     * @param username El nombre del administrador
     */
    public static boolean createAdmin(String username) {
        if(username.equals(""))
            return false;
        accounts.add(new Administrator(username));
        return true;
    }
    /**
     * Crea un Usuario
     * @param username El nombre del usuario
     */
    public static boolean createUser(String username) {
        if(username.equals(""))
            return false;
        accounts.add(new UserDT(username));
        return true;
    }
    /**
     * Retorna una cuenta especifica
     * @param accountname El nombre de la cuenta
     */
    private static Account getAccount(String accountname) {
        for (Account aux: accounts) {
            if (aux.getName().equals(accountname))
                return aux;
        }
        return null;
    }
    /**
     * Guardamos la cuenta que está logeada para poder acceder
     * a sus valores desde varios lugares
     * @param accountName El nombre del usuario
     */
    public static void setAccount(String accountName) {
        account = getAccount(accountName);
    }
    /**
     * Busca al usuario pasado como parametro y si lo contiene retorna true
     * @param username nombre de la cuenta a buscar
     * @return true si lo encuentra, falso en otro caso*/
    public static boolean contains(String username) {
        return !username.equals("") && getAccount(username) != null;
    }
    /**
     * @return Retorna true si al cuenta logeada e User, falso en otro caso
     * */
    public static boolean accountIsUser() {
        return account != null && account instanceof UserDT;
    }

    public static ArrayList<UserDT> getUsersInTournament(Tournament tournament) {
        ArrayList<UserDT> users = null;
        for (Account account: accounts) {
            if (account instanceof Administrator) {
                Administrator admin = (Administrator) account;
                if (admin.containsTournament(tournament)) {
                    users = admin.getOrderedUsers(tournament);
                    break;
                }
            }
        }
        return users;
    }

    /** Carga los usuarios existentes al programa */
    public static void loadAccounts() throws IOException, ClassNotFoundException {
        accounts = (ArrayList<Account>) FileManager.readFromFile("accountsData.temp");
        if (accounts == null)
            accounts = new ArrayList<>();
    }

    /**
     * Guarda la lista de usuario existente
     * */
    public static void save(){
        FileManager.writeToFile(accounts,"accountsData.temp");
    }
    /**
     * Metodo implementeados de la serializacion
     * */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(accounts);
    }
    /**
     * Metodo implementeados de la serializacion
     * */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        accounts = (ArrayList<Account>)ois.readObject();
    }

}
