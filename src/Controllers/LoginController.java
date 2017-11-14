package Controllers;

import Models.AccountsManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController{

    @FXML
    private JFXTextField userTextField;

    @FXML
    private Label error;

    @FXML
    private void handleLogin() {
        String username = userTextField.getText();
        if (!(AccountsManager.contains(username))){
            error.setVisible(true);
        }else{
            AccountsManager.setAccount(username);
            if (AccountsManager.accountIsUser()) {
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

    @FXML
    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            handleLogin();
    }
}
