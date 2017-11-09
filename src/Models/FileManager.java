package Models;

import Controllers.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.*;
import java.net.URL;
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

    static Scene loadFxml(String fileName) throws IOException {
        Parent page = null;
        try {
            URL fileUrl = MainApp.class.getResource("/Resources/Views/" + fileName + ".fxml");
            if(fileUrl == null){
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            page = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            //Esta tirando excepciones aca porque no estan conectados a ningun Controller algunos fxml
            System.out.println(e.getMessage());
        }
        if(page!=null)
            return new Scene(page);
        else
            return null;
    }

}
