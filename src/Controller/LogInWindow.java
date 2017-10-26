package Tester;

import clases.Accounts;
import clases.Cuenta;
import clases.Usuario;
import clases.exceptions.IdAlreadyUsedException;
import clases.exceptions.NombreExistenteException;

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
            if (Accounts.correctInfo(nombreField.getText(), passwdField.getText())){
                login(Accounts.getCuenta(nombreField.getText()));
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
            Usuario usuario = new Usuario(nombreField.getText(),Integer.parseInt(idField.getText()), passwdField.getText());
            try {
                Accounts.addUsuario(usuario);
                System.out.println("hasta ca si");
                login(Accounts.getCuenta(nombreField.getText()));
            } catch (NombreExistenteException | IdAlreadyUsedException e) {
                JOptionPane.showMessageDialog(null, "El nombre o el Id ya existen");
            }
        }
    }

    private void login(Cuenta c){
        this.dispose(); // error aca, no logro hascer que desaparesca
        setVisible(false);
        System.out.println("hasta ca si");
        if (c instanceof Usuario)
            new UserWindow();
        else
            new AdminWindow();
    }
}
