package clases;


import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileMananger implements Serializable {

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
