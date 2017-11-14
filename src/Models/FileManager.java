package Models;

import java.io.*;
import java.util.NoSuchElementException;


class FileManager {

    private static String getResourcesDirectory() {
        File resourcesDirectory = new File("src/Resources");
        return resourcesDirectory.getAbsoluteFile().toString();
    }

    static void writeToFile(Object obj, String fileName){
        String resourceDirectory = getResourcesDirectory();
        ObjectOutputStream outStream = null;
        try {
            outStream = new ObjectOutputStream(new FileOutputStream(resourceDirectory + "/" + fileName));
            outStream.writeObject(obj);
        } catch (IOException ioException) {
            System.err.println("Error opening file.");
        }catch(NoSuchElementException noSuchElementException){
            System.err.println("Invalid input.");
        }finally{
            try {
                if (outStream != null)
                    outStream.close();
            }catch(IOException ioException){
                System.err.println("Error closing file.");
            }
        }
    }

    static Object readFromFile(String fileName) throws ClassNotFoundException {
        String resourceDirectory = getResourcesDirectory();
        String filePath = resourceDirectory + "/" + fileName;
        Object obj = null;
        // si el archivo está vacio, no nos gastamos en abrirlo
        if (new File(filePath).length() != 0){
            ObjectInputStream inputStream;
            try {
                inputStream = new ObjectInputStream(new FileInputStream(filePath));
                obj = inputStream.readObject();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return obj;
    }
}
