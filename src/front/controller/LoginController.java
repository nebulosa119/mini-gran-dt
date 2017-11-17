package front.controller;

import back.model.AccountsManager;
import com.jfoenix.controls.JFXTextField;
import front.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controlador de la ventana de ingreso de usuarios y administradores.
 *
 * @author tdorado
 */
public class LoginController{

    @FXML
    private JFXTextField userTextField;

    @FXML
    private Label error;

    /**
     * Maneja el ingreso a una cuenta, verificando si es usuario normal o administrador.
     * Llama a la siguiente ventana.
     */
    @FXML
    private void handleLogin() {
        String username = userTextField.getText();
        if (!(AccountsManager.contains(username))){
            error.setVisible(true);
        }else{
            AccountsManager.setUser(username);
            if (AccountsManager.accountIsDT()) {
                MainApp.setScene("dtView");
            } else {
                MainApp.setScene("adminView");
            }
        }
    }

    /**
     * Realiza el cambio a la ventana de registro.
     */
    @FXML
    private void handleRegister(){
        MainApp.setScene("registrationView");
    }

    /**
     * Maneja el ingreso de usuarios con la tecla ENTER.
     */
    @FXML
    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            handleLogin();
    }
}
