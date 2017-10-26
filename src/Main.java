package src;

import src.Controller.AccountsMananger;
import src.Controller.Controller;
import src.Controller.UserController;
import src.Model.Account;
import src.Model.User;
import src.View.LogInWindow;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Main {
    public static void main(String[] args){
        // cargando usuers
        Vector<Account> aux = (Vector<Account>)readObjectFromFile("users.temp");
        AccountsMananger users = new AccountsMananger(aux);

        // cargando admins
        aux = (Vector<Account>)readObjectFromFile("users.temp");
        AccountsMananger administrators = new AccountsMananger(aux);

        LogInWindow login = new LogInWindow();
        Controller aux = new UserController()

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
