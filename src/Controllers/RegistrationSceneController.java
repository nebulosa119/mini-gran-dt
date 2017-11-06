package Controllers;

import Models.AccountsManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegistrationSceneController {

    @FXML
    private JFXTextField userTextField;

    @FXML
    private Label error;

    @FXML
    protected void handleGoToLogin() {
        MainApp.setScene("login");
    }

    @FXML
    protected void handleCreateUser(){
        String username = userTextField.getText();
        if (AccountsManager.contains(username)){
            error.setVisible(true);
        }else {
            if(!AccountsManager.createAccount(username))
                error.setVisible(true);
            else
                MainApp.setScene("login");
        }
    }
}
