import Controller.AccountsMananger;
import Controller.Types;
import Model.Administrator;
import Model.User;
import View.LoginWindow;


public class Main {

    public static void main(String[] args) {
        // cargamos usuario
        AccountsMananger<User> users = new AccountsMananger<User>();
        users.loadUsers();
        // cargamos admins
        AccountsMananger<Administrator> admins = new AccountsMananger<Administrator>();
        admins.loadAdmins();

        LoginWindow login = new LoginWindow();
        login.main();
        String username = login.getUserName; // login deberia chequear que haya info
        Types type = login.getType();// con un selecionador tipo de paises
        // recibo el username, no verificar si existe o no
        login.exit();

        if (type.equals(Types.USER)) {
            User user;

            if (users.contains(username)) {
                user = (User) users.getAccount(username);
            } else {
                user = new User(username);
                users.add(user);
            }
            UserWindow.main(user);

        } else {  //Type is ADMIN
            Administrator admin;

            if (admins.contains(username)) {
                admin = (Administrator) admins.getAccount(username);
            } else {
                admin = new Administrator(username);
                admins.add(admin);
            }
            AdminWindow.main(admin);
        }
    }
}
