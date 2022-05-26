package Presentation;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    protected JLabel title = new JLabel("LOGIN");
    protected JLabel username = new JLabel("username");
    protected JLabel password = new JLabel("password");

    protected JTextField usernameTF = new JTextField();
    protected JPasswordField passwordField = new JPasswordField();

    protected JButton login = new JButton("LOGIN");
    protected JButton register = new JButton("REGISTER");

    public LoginView(){

        title.setFont(new Font("Roboto", Font.PLAIN, 24));
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1,50, 5));

        panel.add(username);
        panel.add(usernameTF);
        panel.add(password);
        panel.add(passwordField);

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 2, 100, 50));
        bottom.add(login);
        bottom.add(register);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(100, 10));
        this.setSize(800, 500);
        this.setVisible(true);
        this.add(title, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.SOUTH);
        this.add(panel, BorderLayout.CENTER);
        this.pack();
    }

    public JButton getRegister() {
        return register;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLogin() {
        return login;
    }

    public JTextField getUsernameTF() {
        return usernameTF;
    }
}
