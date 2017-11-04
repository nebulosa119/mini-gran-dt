package Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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
            System.out.println("no Existe");
        }
        AccountsManager.setAccount(username);
        if (AccountsManager.accountIsUser()){
            MainApp.setScene("TeamSelection");
        }else {
            MainApp.setScene("TransitionScene");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
