package Controller;

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
        MainApp.getInstance().accountLogging(userTextField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
