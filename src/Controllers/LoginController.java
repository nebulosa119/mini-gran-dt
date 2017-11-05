package Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class LoginController{

    @FXML
    private JFXTextField userTextField;

    @FXML
    protected void handleClose(){
        System.exit(0);
    }

    @FXML
    protected void handleLogin() {
        String username = userTextField.getText();
        //buscamos cual tiene al usuario
        if (!AccountsManager.contains(username)){
            // si no existe lo creamos como usuario
            AccountsManager.createAccount(username);
        }
        AccountsManager.setAccount(username);
        if (AccountsManager.accountIsUser()){
            MainApp.setScene("TeamSelection");
        }else {
            MainApp.setScene("TransitionScene");
        }
    }
}
