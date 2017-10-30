package Controller;

import Model.Account;
import Model.User;
import View.LoginWindow;

public class LoginController {

    private AccountsManager accounts;

    public LoginController(Controller.AccountsManager accounts) {
        this.accounts = accounts;
    }

    public Account start() {

        LoginWindow login = new LoginWindow();

        login.main(); //COMO LLAMO AL MAIN PARA QUE ARRANQUE LA APP?
        String username = login.getUserName(); // login deberia chequear que haya info
        Types type = login.getType();// con un selecionador tipo de paises
        // recibo el username, no verificar si existe o no
        login.exit();

        if (!accounts.contains(username))
            User newUser = new User();
            accounts.createAccount(username);// se  crea un usuario
        return accounts.getAccount(username);
    }
}
