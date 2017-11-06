package Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController{

    @FXML
    private JFXTextField userTextField;

    @FXML
    private Label error;

    @FXML
    protected void handleLogin() {
        String username = userTextField.getText();
        //buscamos cual tiene al usuario
        if (!AccountsManager.contains(username)){
            error.setVisible(true);
        }else{
            AccountsManager.setAccount(username);
            if (AccountsManager.accountIsUser()) {
                MainApp.setScene("teamSelection");
            } else {
                MainApp.setScene("transitionScene");
            }
        }
    }

    @FXML
    protected void handleRegister(){
        MainApp.setScene("registrationScene");
    }
}
