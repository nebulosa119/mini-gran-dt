package model;

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

    private static ArrayList<User> users;
    private static User user;

    /**
     * Retrona el usuario que está logeado
     */
    public static User getSignedAccount() {
        return user;
    }
    /**
     * Retrona una lista con todos aquellos que sean administradores
     */
    public static ArrayList<Administrator> getAdmins() {
        ArrayList<Administrator> aux = new ArrayList<>();
        for(User a : users) {
            if(a instanceof Administrator)
                aux.add((Administrator)a);
        }
        return aux;
    }
    /**
     * Retrona los torneos del administrador que está logeado
     */
    public static Set<PhysicalTournament> getTournaments() {
        return ((Administrator) user).getTournaments();
    }
    /**
     * Crea un administrador
     * @param username El nombre del administrador
     */
    public static boolean createAdmin(String username) {
        if(username.equals(""))
            return false;
        users.add(new Administrator(username));
        return true;
    }
    /**
     * Crea un Usuario
     * @param username El nombre del usuario
     */
    public static boolean createDT(String username) {
        if(username.equals(""))
            return false;
        users.add(new DT(username));
        return true;
    }
    /**
     * Retorna una cuenta especifica
     * @param accountname El nombre de la cuenta
     */
    private static User getAccount(String accountname) {
        for (User aux: users) {
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
    public static void setUser(String accountName) {
        user = getAccount(accountName);
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
    public static boolean accountIsDT() {
        return user != null && user instanceof DT;
    }
    /**
     * @return Retorna un array de los DTs en el toreno
     * */
    public static ArrayList<DT> getDTsInTournament(PhysicalTournament physicalTournament) {
        ArrayList<DT> users = null;
        for (User user : AccountsManager.users) {
            if (user instanceof Administrator) {
                Administrator admin = (Administrator) user;
                if (admin.containsTournament(physicalTournament)) {
                    users = admin.getOrderedDTs(physicalTournament);
                    break;
                }
            }
        }
        return users;
    }

    /** Carga los usuarios existentes al programa */
    public static void loadAccounts() throws IOException, ClassNotFoundException {
        users = (ArrayList<User>) FileManager.readFromFile("accountsData.temp");
        if (users == null)
            users = new ArrayList<>();
    }

    /**
     * Guarda la lista de usuario existente
     * */
    public static void save(){
        FileManager.writeToFile(users,"accountsData.temp");
    }
    /**
     * Metodo implementeados de la serializacion
     * */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(users);
    }
    /**
     * Metodo implementeados de la serializacion
     * */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        users = (ArrayList<User>)ois.readObject();
    }

}
