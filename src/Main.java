import Controller.AccountsMananger;
import Controller.Controller;
import Controller.UserController;
import Model.Account;
import Model.User;
import View.LogInWindow;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Vector;

public class Main {
    public static void main(String[] args){
        // agarrando el path de los archivos
        /*URL sourcePath = Main.class.getResource("Main.class");
        String filesPath = sourcePath.toString()*/
        // cargando usuers
        Vector<Account> aux = (Vector<Account>)readObjectFromFile(fie);
        AccountsMananger users = new AccountsMananger(aux);

        // cargando admins
        aux = (Vector<Account>)readObjectFromFile("admins.temp");
        AccountsMananger administrators = new AccountsMananger(aux);

        LogInWindow login = new LogInWindow();
        String name = login.getName();

        Account account = users.getAccount(name);
        if (account == null){
            account = new User(name, login.getId());
            users.add(account);
        }

        UserWindow view = new UserWindow();
        Controller controller = new UserController(account,view);

    }

    public static void writeObjectToFile(Object o, String file) {
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Object readObjectFromFile(String file) {
        Object resp = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            resp = ois.readObject();
            ois.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return resp;
    }
}
