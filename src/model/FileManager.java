package model;

import java.io.*;
import java.util.NoSuchElementException;

/**
 * Se ocupa de guardar la inforacion del Juego, es llamada por AccountsManager
 * cuando está necesita buscar informacion y cuando necesita guardar*/
class FileManager {

    /**Busca el path al directorio donde están guardados los archivos
     * @return el path del directorio*/
    private static String getResourcesDirectory() {
        File resourcesDirectory = new File("src/resources");
        return resourcesDirectory.getAbsoluteFile().toString();
    }
    /**
     * Guarda un objeto en el archivo
     * @param obj el objeto a guardar
     * @param fileName el nombre del archivo donde guardar el objeto*/
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
    /**
     * Busca un objeto en el archivo
     * @param fileName el nombre del archivo donde buscar el objeto*/
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
