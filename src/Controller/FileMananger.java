package Controller;

import java.io.*;

public class FileMananger {

    public static void writeObjectToFile(Object o, String file) {
        file = getResourcesDirectory()+"/"+file;
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static String getResourcesDirectory() {
        File resourcesDirectory = new File("src/resources");
        return resourcesDirectory.getAbsoluteFile().toString();
    }

    public static Object readObjectFromFile(String file) {
        file = getResourcesDirectory()+"/"+file;
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
    public static void serializeObject(Object obj, String fileName) {
        String resourceDirectory = getResourcesDirectory();
        try {
            FileOutputStream fileOut = new FileOutputStream(resourceDirectory+"/"+fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
            System.out.printf("Serialized "+obj+"to file "+fileName);
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public static Object unserializeObject(String fileName) {
        String resourceDirectory = getResourcesDirectory();
        Object obj = null;
        try {
            FileInputStream fileIn = new FileInputStream(resourceDirectory + "/"+fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
    //
//    public static void main(String[] args) {
//        File resourcesDirectory = new File("src/resources");
//        String resourceDirectory = resourcesDirectory.getAbsoluteFile().toString();
//
//        String respath = resourceDirectory+"/users.temp";
//
//        Vector vec = new Vector();
//        vec.add(3);
//        vec.add(5);
//        vec.add(5);
//        vec.add(2345);
//        String hola = new String("hola asdfa");
//
//        writeObjectToFile(vec,respath);
//
//        Vector object = (Vector) readObjectFromFile(respath);
//        System.out.println(object);
//    }

}
