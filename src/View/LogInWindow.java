package View;

import Controller.AccountsMananger;
import Model.Account;
import Model.User;
import Model.exceptions.ExistentNameException;
import Model.exceptions.IdAlreadyUsedException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LogInWindow extends View {
    private JTextField dniTextField;
    private JTextField userNameTextField;
    private JTextArea welcomText;
    private JButton logInButton;
    private JPanel mainPanel;

    public LogInWindow() {
        logInButton.addActionListener(new LogInWindow.AttemptLogIn());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LogIn Menu");

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setContentPane(new JLabel(new ImageIcon(img)));

        frame.setContentPane(new LogInWindow().mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
    }


    private class AttemptLogIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            if (AccountsMananger.correctInfo(userNameTextField.getText())){
                //login(AccountsMananger.getAccount(nombreField.getText()));
            }else {
                JOptionPane.showMessageDialog(null, "Incorrect Info");
            }
        }
    }
//
//    private void login(Account c){
//        this.dispose(); // error aca, no logro hascer que desaparesca
//        setVisible(false);
//        System.out.println("hasta ca si");
//        if (c instanceof User)
//            new UserWindow();
//        else
//            new AdminWindow();
//    }


}
