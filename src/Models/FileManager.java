package Models;

import Controllers.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.*;
import java.net.URL;


public class FileManager {

    private static String getResourcesDirectory() {
        File resourcesDirectory = new File("src/Resources");
        return resourcesDirectory.getAbsoluteFile().toString();
    }

    public static void serializeObject(Object obj, String fileName) {
        String resourceDirectory = getResourcesDirectory();
        try {
            FileOutputStream fileOut = new FileOutputStream(resourceDirectory+"/"+fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    static Object unserializeObject(String fileName) {
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
