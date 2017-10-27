package Controller;

import Model.Account;
import View.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Controller {
    protected Account model;
    protected View view;

     Controller(Account model, View view) {
        this.model = model;
        this.view = view;
    }

    public static void main(String[] args) {
        AccountsMananger userAcc = new A
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
