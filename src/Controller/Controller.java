package Controller;

import Controller.*;
import Model.*;
import View.*;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;


public class Controller extends Application {
    private Stage stage;
    private View view;
    private AccountsManager accounts;
    private String username;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // guardamos el stage para manipularlo desde cualquier parte de la clase
        stage = primaryStage;
        // guardamos los accoutns para manipularlos desde cualqeuir parte
        accounts = new AccountsManager();
        accounts.loadAccounts();
        // creando login
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene loginScene = new Scene(root);

        // logeamos
        primaryStage.setTitle("Mini Gran DT");

        primaryStage.setScene(loginScene);
        primaryStage.show();

    }


    @FXML
    private JFXTextField userTextField;

    @FXML
    public void handleClose(){
        System.exit(0);
    }

    @FXML
    public void handleLogin(){
        if(!userTextField.getText().isEmpty()){
            username = userTextField.getText();
            loadView(username);
            stage.setScene(view.createScene());
        }
    }

    private void loadView(String username) {
        Scene returnScene;
        // buscamos cual tiene al usuario
        if (!accounts.contains(username)){
            // si no existe lo creamos como usuario
            accounts.createAccount(username);
        }
        Account account = accounts.getAccount(username);
        if (account instanceof User){
            view = new UserView(this);
        }else
            view = new AdminView(this);
    }
    @Override
    public void stop() throws Exception {
        accounts.save();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
