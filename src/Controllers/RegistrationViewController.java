package Controllers;

import Models.AccountsManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controlador de la ventana de registro de usuarios y administradores.
 *
 * @author tdorado
 */
public class RegistrationViewController {

    @FXML
    private JFXTextField userTextField;

    @FXML
    private CheckBox admin_account_check;

    @FXML
    private Label error;

    /**
     * Realiza el cambio a la ventana de ingreso.
     */
    @FXML
    protected void handleGoToLogin() {
        MainApp.setScene("login");
    }

    /**
     * Maneja la creación de una cuenta, con opcion de usuario normal o administrador.
     * Llama a la siguiente ventana.
     */
    @FXML
    protected void handleCreateUser() {
        String username = userTextField.getText();
        if (AccountsManager.contains(username)) {
            error.setVisible(true);
        } else {
            boolean wantsAdmin = admin_account_check.isSelected();
            if (wantsAdmin && !AccountsManager.createAdmin(username))
                error.setVisible(true);
            else if (!AccountsManager.createUser(username))
                error.setVisible(true);
            else
                MainApp.setScene("login");
        }
    }

    /**
     * Maneja la creación de cuentas con la tecla ENTER.
     */
    @FXML
    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            handleCreateUser();
    }
}
