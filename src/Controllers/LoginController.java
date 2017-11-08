package Controllers;

import Models.AccountsManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController{

    @FXML
    private JFXTextField userTextField;

    @FXML
    private Label error;

    @FXML
    private void handleLogin() {
        String username = userTextField.getText();
        //buscamos cual tiene al usuario
        if (!AccountsManager.getInstance().contains(username)){
            error.setVisible(true);
        }else{
            AccountsManager.getInstance().setAccount(username);
            if (AccountsManager.getInstance().accountIsUser()) {
                    MainApp.setScene("userView");
            } else {
                MainApp.setScene("adminView");
            }
        }
    }

    @FXML
    private void handleRegister(){
        MainApp.setScene("registrationScene");
    }
}
