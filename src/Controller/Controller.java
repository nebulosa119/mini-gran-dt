package Controller;

import Controller.AdminController.AdminController;
import Model.Account;
import Model.Administrator;
import Model.User;
import javafx .fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private LoginController loginC;
    private AccountsManager accounts;
    private AdminController adminC;
    private UserController userC;
    private TeamController teamC;
    private TournamentController tournamentC;

    @FXML
    private Label label;

    @FXML
    private void handleClose(MouseEvent event){
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void start() {

        //Cargamos las cuentas de admins y users
        accounts = new AccountsManager();
        accounts.loadAccounts();

        loginC = new LoginController(accounts);

        Account account = loginC.start();

        if (account.isAdmin()) {
            adminC = new AdminController((Administrator)account);
            adminC.start(); // aca adentro se manejan los refresh, create team, bla bla..
        } else {
            userC = new UserController((User)account);
            userC.start(); // aca se maneja el create team, modify team, bla bla..
        }
    }
}
