package Controller;

import Model.Account;
import View.LoginWindow;

public class LoginController {

    private AccountsMananger accounts;

    public LoginController(AccountsMananger accounts) {
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
            accounts.createAccount(username, type);
        return accounts.getAccount(username);

        accounts.save();
    }
}
