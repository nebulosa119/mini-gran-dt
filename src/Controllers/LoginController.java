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
        //buscamos cual tiene al usuario
        if (!(AccountsManager.getInstance().contains(username))){
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

    @FXML
    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            handleLogin();
    }
}
