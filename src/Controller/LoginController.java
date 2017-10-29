package Controller;

import Model.Administrator;
import Model.User;
import View.LoginWindow;

public class LoginController extends Controller {
    @Override
    public void start() {
        // cargamos usuarios
        AccountsMananger<User> users = new AccountsMananger<User>();
        users.loadUsers();
        // cargamos admins
        AccountsMananger<Administrator> admins = new AccountsMananger<Administrator>();
        admins.loadAdmins();
/*
        LoginWindow login = new LoginWindow();
        login.main();
        String username = login.getUserName(); // login deberia chequear que haya info
        Types type = login.getType();// con un selecionador tipo de paises
        // recibo el username, no verificar si existe o no
        login.exit();

        Controller controller;
        if (type.equals(Types.USER)){
            User user;
            if (users.contains(username)) {
                user = (User) users.getAccount(username);
            }else{
                user = new User(username);
                users.add(user);
            }
            controller = new UserController(user);
        }else{
            Administrator admin;
            if (admins.contains(username)) {
                admin = (Administrator) admins.getAccount(username);
            }else{
                admin = new Administrator(username);
                admins.add(admin);
            }
            controller = new AdminController(admin);
        }
        controller.start();
        users.save();
        admins.save();
        */
    }
}
