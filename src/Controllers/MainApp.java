package Controllers;

import java.net.URL;

import Models.AccountsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage stage;
    private static MainApp instance;

    public MainApp(){
        instance = this;
    }

    public static MainApp getInstance(){
        if(instance==null)
            instance = new MainApp();
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        AccountsManager.loadAccounts();
        stage = primaryStage;
        stage.setTitle("Mini Gran DT");
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/Resources/Media/icon.png")));
        setScene("login");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        AccountsManager.save();
        super.stop();
    }

    public static void setScene(String windowName){
        Parent page = null;
        try {
            URL fileUrl = MainApp.class.getResource("/Resources/Views/" + windowName + ".fxml");
            page = FXMLLoader.load(fileUrl);
        } catch (Exception e) {
            System.out.println("FXML " + windowName +" loading error.");
        }
        stage.setScene(new Scene(page));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
