package Controller;

import Controller.*;
import Model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        Scene loginScene = new Scene(createLogInWindow(), 300, 275);

        // logeamos
        primaryStage.setTitle("Mini Gran DT");

        primaryStage.setScene(loginScene);
        primaryStage.show();

    }


    private VBox createLogInWindow(){

        TextField wellcomeTextField = new TextField("Welcome to Mini Gran DT");
        wellcomeTextField.setEditable(false);

        TextField userTextField = new TextField("Username");

        Button loginButton = new Button("LogIn/Create Account");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!userTextField.getText().isEmpty(){
                    username = userTextField.getText();
                    loadView(username);
                    stage.setScene(view.createScene());
                }
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(wellcomeTextField,userTextField,loginButton);

        return vBox;
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
}
