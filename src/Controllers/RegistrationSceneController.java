package Controllers;

import Models.AccountsManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
        if (AccountsManager.getInstance().contains(username)){
            error.setVisible(true);
        }else {
            if(!AccountsManager.getInstance().createAccount(username))
                error.setVisible(true);
            else
                MainApp.setScene("login");
        }
    }

    @FXML
    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            handleCreateUser();
    }
}
