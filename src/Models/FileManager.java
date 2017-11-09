package Models;

import java.io.*;
import java.util.NoSuchElementException;


public class FileManager {

    private static String getResourcesDirectory() {
        File resourcesDirectory = new File("src/Resources");
        return resourcesDirectory.getAbsoluteFile().toString();
    }

    public static void writeToFile(Object obj, String fileName){
        String resourceDirectory = getResourcesDirectory();
        ObjectOutputStream outStream = null;
        try {
            outStream = new ObjectOutputStream(new FileOutputStream(resourceDirectory + "/" + fileName));
            outStream.writeObject(obj);
        } catch (IOException ioException) {
            System.err.println("Error opening file.");
        }catch(NoSuchElementException noSuchElementExcaption){
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

    static Object readFromFile(String fileName) {
        String resourceDirectory = getResourcesDirectory();
        Object obj = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(resourceDirectory + "/" + fileName));
            obj = inputStream.readObject();
        } catch (EOFException eofException) {
            return obj;
        } catch (ClassNotFoundException classNetFoundException) {
            System.err.println("Object creation failed.");
        } catch (IOException ioException) {
            System.err.println("Error opening file.");
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ioException) {
                System.err.println("Error closing file.");
            }
        }
        return obj;
    }
}
