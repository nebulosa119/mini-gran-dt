package src.View;

import src.Controller.AccountsMananger;
import src.Model.Account;
import src.Model.User;
import src.Model.exceptions.ExistentNameException;
import src.Model.exceptions.IdAlreadyUsedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInWindow extends JFrame{
    private JButton loginButton;
    private JPanel panelMain;
    private JTextField nombreField;
    private JTextField passwdField;
    private JButton createButton;
    private JTextField idField;
    private JTextArea welcomeField;

    public LogInWindow() {
        idField.setVisible(false);
        loginButton.addActionListener(new AttemptLogIn());
        createButton.addActionListener(new CreateAccount());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LogIn Menu");
        frame.setContentPane(new LogInWindow().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
    }


    private class AttemptLogIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            if (AccountsMananger.correctInfo(nombreField.getText(), passwdField.getText())){
                login(AccountsMananger.getAccount(nombreField.getText()));
            }else {
                JOptionPane.showMessageDialog(null, "Incorrect Info");
            }
        }
    }
    private class CreateAccount implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a) {
            loginButton.setVisible(false);
            idField.setVisible(true);
            User user = new User(nombreField.getText(),Integer.parseInt(idField.getText()), passwdField.getText());
            try {
                AccountsMananger.addUser(user);
                System.out.println("hasta ca si");
                login(AccountsMananger.getAccount(nombreField.getText()));
            } catch (ExistentNameException | IdAlreadyUsedException e) {
                JOptionPane.showMessageDialog(null, "El nombre o el Id ya existen");
            }
        }
    }

    private void login(Account c){
        this.dispose(); // error aca, no logro hascer que desaparesca
        setVisible(false);
        System.out.println("hasta ca si");
        if (c instanceof User)
            new UserWindow();
        else
            new AdminWindow();
    }
}
