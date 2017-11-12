package Models;

import java.io.*;
import java.util.NoSuchElementException;


public class FileManager {

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

    static Object readFromFile(String fileName) throws IOException, ClassNotFoundException {
        String resourceDirectory = getResourcesDirectory();;
        ObjectInputStream inputStream = null;
        inputStream = new ObjectInputStream(new FileInputStream(resourceDirectory + "/" + fileName));
        Object obj = inputStream.readObject();
        inputStream.close();
        return obj;
    }
}
