import Controller.AccountsMananger;
import Controller.Types;
import Model.Administrator;
import Model.User;
import View.Login;


public class Main {

    public static void main(String[] args) {
        // cargamos usuario
        AccountsMananger<User> users = new AccountsMananger<User>();
        users.loadUsers();
        // cargamos admins
        AccountsMananger<Administrator> admins = new AccountsMananger<Administrator>();
        admins.loadAdmins();

        LogInWindow login = new LoginWindow();
        login.main();
        String username = login.getUserName; // login deberia chequear que haya info
        Types type = login.getType();// con un selecionador tipo de paises
        // recibo el username, no verificar si existe o no
        login.exit();

        /////////////////////////////////////////////
        /// esto quedó horrible, hay que cambiarlo

        if (users.contains(username)){
            User user = (User)users.getAccount(username);
            UserWindow.main(user);// <- deberia tener/llamar metodo refresh()
        }else if(admins.contains(username)){
            Administrator admin = (Administrator) admins.getAccount(username);
            AdminWindow.main(admin);
        }else{
            if (type.equals(Types.USER)){
                User user = new User(username);
                users.add(user);
                UserWindow.main(user);}
            else {
                Administrator admin = new Administrator(username);
                admins.add(admin);
                AdminWindow.main(admin);}
        }
        ////////////////////////////////////////
    }
}
