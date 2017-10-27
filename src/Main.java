import Controller.AccountsMananger;
import Controller.Types;
import Model.Administrator;
import Model.User;
import View.Login;


public class Main {

    public static void main(String[] args) {

        AccountsMananger<User> users = new AccountsMananger<User>();
        users.loadUsers();

        AccountsMananger<Administrator> admins = new AccountsMananger<Administrator>();
        admins.loadAdmins();

        LoginWindow.main();
        String username = Login.getUserName;
        String type = Login.getType();// con un selecionador tipo de paises

        LogInWindow.exit();

        /////////////////////////////////////////////
        /// esto quedó horrible, hay que cambiarlo
        if (users.contains(username)){
            User user = (User)users.getAccount(username);
            UserWindow.main(user);
        }else if(admins.contains(username)){
            Administrator admin = (Administrator) admins.getAccount(username);
            AdminWindow.main(admin);
        }else{
            if (type.equals(Types.USER.name)){
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
